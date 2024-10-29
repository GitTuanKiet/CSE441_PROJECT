package com.cse_411_project.aigy.features.agents.interactor

import com.cse_411_project.aigy.core.interactor.UseCase
import com.cse_411_project.aigy.features.agents.data.AgentStoreRepository
import com.cse_411_project.aigy.features.agents.interactor.GetAgentDetails.Params

class GetAgentDetails (
    private val repository: AgentStoreRepository
) : UseCase<AgentDetails, Params>() {
    override suspend fun run(params: Params) = repository.agentDetails(params.identifier);

    data class Params(val identifier: String)
}