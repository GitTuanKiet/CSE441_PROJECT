package com.cse_411_project.aigy.features.agents.interactor

import com.cse_411_project.aigy.core.interactor.UseCase
import com.cse_411_project.aigy.core.interactor.UseCase.None
import com.cse_411_project.aigy.features.agents.data.AgentStoreRepository

class GetAgents(
    private val repository: AgentStoreRepository
) : UseCase<List<Agent>, None>() {

    override suspend fun run(params: None) = repository.agents()
}
