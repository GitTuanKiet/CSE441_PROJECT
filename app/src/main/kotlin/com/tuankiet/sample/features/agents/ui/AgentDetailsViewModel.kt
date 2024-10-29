package com.tuankiet.sample.features.agents.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.tuankiet.sample.core.interactor.UseCase.None
import com.tuankiet.sample.core.platform.BaseViewModel
import com.tuankiet.sample.features.agents.interactor.AgentDetails
import com.tuankiet.sample.features.agents.interactor.GetAgentDetails
import com.tuankiet.sample.features.agents.interactor.GetAgentDetails.Params

class AgentDetailsViewModel(private val getAgentDetails: GetAgentDetails) : BaseViewModel() {

    private val _agentDetails: MutableLiveData<AgentDetailsView> = MutableLiveData()
    val agentDetails: LiveData<AgentDetailsView> = _agentDetails

    fun loadAgentDetails(identifier: String) =
        getAgentDetails(Params(identifier), viewModelScope) { it.fold(::handleFailure, ::handleAgentDetails) }

    private fun handleAgentDetails(agentDetails: AgentDetails) {
        _agentDetails.value = AgentDetailsView(agentDetails.identifier, agentDetails.author, AgentConfigView(agentDetails.config.systemRoles), AgentMetaView(agentDetails.meta.title, agentDetails.meta.description, agentDetails.meta.avatar, agentDetails.meta.tags, agentDetails.meta.category))
    }

    fun agentChat(identifier: String) {
        _agentDetails.value = AgentDetailsView(identifier, "Chat", AgentConfigView(listOf("Chat").toString()), AgentMetaView("Chat", "Chat", "Chat", listOf("Chat"), "Chat"))
    }
}