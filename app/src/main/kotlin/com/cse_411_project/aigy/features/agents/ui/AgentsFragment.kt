package com.cse_411_project.aigy.features.agents.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.extension.failure
import com.cse_411_project.aigy.core.extension.invisible
import com.cse_411_project.aigy.core.extension.observe
import com.cse_411_project.aigy.core.extension.visible
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.navigation.Navigator
import com.cse_411_project.aigy.core.platform.BaseFragment
import com.cse_411_project.aigy.databinding.FragmentAgentsBinding
import com.cse_411_project.aigy.features.agents.failure.AgentFailure
import org.koin.android.ext.android.inject

class AgentsFragment : BaseFragment() {

    private val navigator: Navigator by inject()
    private val agentsAdapter: AgentsAdapter by inject()

    private val agentsViewModel: AgentsViewModel by inject()

    private var _binding: FragmentAgentsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(agentsViewModel) {
            observe(agents, ::renderAgentsList)
            failure(failure, ::handleFailure)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeView()
        loadAgentsList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initializeView() {
        binding.agentList.layoutManager = StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.agentList.adapter = agentsAdapter
        agentsAdapter.clickListener = { agent, navigationExtras ->
            navigator.showAgentDetails(requireActivity(), agent, navigationExtras)
        }
    }

    private fun loadAgentsList() {
        binding.emptyView.invisible()
        binding.agentList.visible()
        showProgress()
        agentsViewModel.loadAgents()
    }

    private fun renderAgentsList(agents: List<AgentView>?) {
        agentsAdapter.collection = agents.orEmpty()
        hideProgress()
    }

    private fun handleFailure(failure: Failure?) {
        when (failure) {
            is Failure.NetworkConnection -> renderFailure(R.string.failure_network_connection)
            is Failure.ServerError -> renderFailure(R.string.failure_server_error)
            is AgentFailure.ListNotAvailable -> renderFailure(R.string.failure_agents_list_unavailable)
            else -> renderFailure(R.string.failure_server_error)
        }
    }

    private fun renderFailure(@StringRes message: Int) {
        binding.agentList.invisible()
        binding.emptyView.visible()
        hideProgress()
        notifyWithAction(message, R.string.action_refresh, ::loadAgentsList)
    }
}
