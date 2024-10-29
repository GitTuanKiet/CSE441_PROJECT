package com.tuankiet.sample.features.agents.interactor

import com.tuankiet.sample.core.interactor.UseCase
import com.tuankiet.sample.features.agents.data.AgentStoreRepository
import com.tuankiet.sample.features.agents.interactor.GetAgentDetails.Params

class GetAgentDetails (
    private val repository: AgentStoreRepository
) : UseCase<AgentDetails, Params>() {
    override suspend fun run(params: Params) = repository.agentDetails(params.identifier);

    data class Params(val identifier: String)
}