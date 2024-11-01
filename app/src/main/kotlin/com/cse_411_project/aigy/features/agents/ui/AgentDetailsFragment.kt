package com.cse_411_project.aigy.features.agents.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.extension.*
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.failure.Failure.*
import com.cse_411_project.aigy.core.navigation.Navigator
import com.cse_411_project.aigy.core.platform.BaseFragment
import com.cse_411_project.aigy.databinding.FragmentAgentDetailsBinding
import com.cse_411_project.aigy.features.chat.WelcomeAgentActivity
import com.cse_411_project.aigy.features.agents.failure.AgentFailure.*
import org.koin.android.ext.android.inject

class AgentDetailsFragment : BaseFragment() {

    companion object {
        private const val PARAM_AGENT = "param_agent"

        fun forAgent(agent: AgentView?) = AgentDetailsFragment().apply {
            arguments = bundleOf(PARAM_AGENT to agent)
        }
    }

    private val navigator: Navigator by inject()

    private val agentDetailsViewModel: AgentDetailsViewModel by inject()

    private var _binding: FragmentAgentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(agentDetailsViewModel) {
            observe(agentDetails, ::renderAgentDetails)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val agent = arguments?.getParcelable(PARAM_AGENT, AgentView::class.java)
        agent?.identifier?.let {
            agentDetailsViewModel.loadAgentDetails(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderAgentDetails(agent: AgentDetailsView?) {
        agent?.let {
            with(agent) {
                activity?.let {
                    binding.agentAvatar.loadUrlAndPostponeEnterTransition(meta.avatar, it)
                }
                binding.agentTitle.text = meta.title
                binding.agentAuthor.text = author
                binding.agentDescription.text = meta.description
                binding.agentSystemRole.text = config.systemRole
                binding.agentChat.setOnClickListener {
                    navigator.showWelcomeAgent(requireActivity(), agent)
                }
            }
        }
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