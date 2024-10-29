package com.cse_411_project.aigy.features.agents.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cse_411_project.aigy.core.interactor.UseCase.None
import com.cse_411_project.aigy.core.platform.BaseViewModel
import com.cse_411_project.aigy.features.agents.interactor.Agent
import com.cse_411_project.aigy.features.agents.interactor.GetAgents

class AgentsViewModel(private val getAgents: GetAgents) : BaseViewModel() {

    private val _agents: MutableLiveData<List<AgentView>> = MutableLiveData()
    val agents: LiveData<List<AgentView>> = _agents

    fun loadAgents() =
        getAgents(None(), viewModelScope) { it.fold(::handleFailure, ::handleAgentList) }

    private fun handleAgentList(agents: List<Agent>) {
        _agents.value = agents.map { AgentView(it.identifier, it.author, AgentMetaView(it.meta.title, it.meta.description, it.meta.avatar, it.meta.tags, it.meta.category)) }
    }
}
