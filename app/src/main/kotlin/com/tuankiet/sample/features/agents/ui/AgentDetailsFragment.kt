package com.tuankiet.sample.features.agents.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tuankiet.sample.R
import com.tuankiet.sample.core.extension.*
import com.tuankiet.sample.core.failure.Failure
import com.tuankiet.sample.core.failure.Failure.*
import com.tuankiet.sample.core.navigation.Navigator
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.FragmentAgentDetailsBinding
import com.tuankiet.sample.databinding.FragmentAgentsBinding
import com.tuankiet.sample.features.agents.failure.AgentFailure.*
import org.koin.android.ext.android.inject

class AgentDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_AGENT = "param_agent"

        fun forAgent(agent: AgentView?) = AgentDetailsFragment().apply {
            arguments = bundleOf(PARAM_AGENT to agent)
        }
    }

    private val agentDetailsAnimator: AgentDetailsAnimator by inject()

    private val agentDetailsViewModel: AgentDetailsViewModel by inject()

    private var _binding: FragmentAgentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let { agentDetailsAnimator.postponeEnterTransition(it) }

        with(agentDetailsViewModel) {
            observe(agentDetails, ::renderAgentDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            agentDetailsViewModel.loadAgentDetails((arguments?.get(PARAM_AGENT) as AgentView).identifier)
        } else {
            agentDetailsAnimator.scaleUpView(binding.agentChat)
            agentDetailsAnimator.cancelTransition(binding.agentAvatar)
            binding.agentAvatar.loadFromUrl((requireArguments()[PARAM_AGENT] as AgentView).meta.avatar)
        }
    }

    override fun onBackPressed() {
        agentDetailsAnimator.fadeInvisible(binding.scrollView, binding.agentDetails)
        if (binding.agentChat.isVisible())
            agentDetailsAnimator.scaleDownView(binding.agentChat)
        else
            agentDetailsAnimator.cancelTransition(binding.agentAvatar)
    }

    private fun renderAgentDetails(agent: AgentDetailsView?) {
        agent?.let {
            with(agent) {
                activity?.let {
                    binding.agentAvatar.loadUrlAndPostponeEnterTransition(meta.avatar, it)
                    it.title = meta.title
                }
                binding.agentAuthor.text = author
                binding.agentDescription.text = meta.description
                binding.agentCategory.text =  meta.category
                binding.agentChat.setOnClickListener { agentDetailsViewModel.agentChat(identifier) }
            }
        }
        agentDetailsAnimator.fadeVisible(binding.scrollView, binding.agentDetails)
        agentDetailsAnimator.scaleUpView(binding.agentChat)
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is NetworkConnection -> {
                notify(R.string.failure_network_connection); close()
            }
            is ServerError -> {
                notify(R.string.failure_server_error); close()
            }
            is NonExistentAgent -> {
                notify(R.string.failure_agent_non_existent); close()
            }
            else -> {
                notify(R.string.failure_server_error); close()
            }
        }
    }
}