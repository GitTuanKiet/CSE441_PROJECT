package com.tuankiet.sample.features.agents.interactor

import com.tuankiet.sample.core.interactor.UseCase
import com.tuankiet.sample.core.interactor.UseCase.None
import com.tuankiet.sample.features.agents.data.AgentStoreRepository

class GetAgents(
    private val repository: AgentStoreRepository
) : UseCase<List<Agent>, None>() {

    override suspend fun run(params: None) = repository.agents()
}
