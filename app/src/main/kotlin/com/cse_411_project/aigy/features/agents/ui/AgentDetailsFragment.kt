package com.cse_411_project.aigy.features.agents.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.extension.*
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.failure.Failure.*
import com.cse_411_project.aigy.core.platform.BaseFragment
import com.cse_411_project.aigy.databinding.FragmentAgentDetailsBinding
import com.cse_411_project.aigy.features.agents.failure.AgentFailure.*
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (firstTimeCreated(savedInstanceState)) {
            (arguments?.getParcelable(PARAM_AGENT, AgentView::class.java))?.identifier?.let {
                agentDetailsViewModel.loadAgentDetails(
                    it
                )
            }
        } else {
            agentDetailsAnimator.translateLeftView(binding.agentChat)
            agentDetailsAnimator.cancelTransition(binding.agentAvatar)
            (arguments?.getParcelable(PARAM_AGENT, AgentView::class.java))?.meta?.let {
                binding.agentAvatar.loadFromUrl(
                    it.avatar
                )
            }
        }
    }

    override fun onBackPressed() {
        agentDetailsAnimator.fadeInvisible(binding.scrollView, binding.agentDetails)
        if (binding.agentChat.isVisible())
            agentDetailsAnimator.translateRightView(binding.agentChat)
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
                binding.agentChat.setOnClickListener {
                    agentDetailsViewModel.agentChat(identifier) }
            }
        }
        agentDetailsAnimator.fadeVisible(binding.scrollView, binding.agentDetails)
        agentDetailsAnimator.translateLeftView(binding.agentChat)
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