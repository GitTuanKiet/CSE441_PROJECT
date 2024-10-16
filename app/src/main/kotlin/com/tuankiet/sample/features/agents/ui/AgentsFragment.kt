package com.tuankiet.sample.features.agents.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.tuankiet.sample.R
import com.tuankiet.sample.core.extension.failure
import com.tuankiet.sample.core.extension.invisible
import com.tuankiet.sample.core.extension.observe
import com.tuankiet.sample.core.extension.visible
import com.tuankiet.sample.core.failure.Failure
import com.tuankiet.sample.core.navigation.Navigator
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.FragmentAgentsBinding
import com.tuankiet.sample.features.agents.failure.AgentFailure
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
