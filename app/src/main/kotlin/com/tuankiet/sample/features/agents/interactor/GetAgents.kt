package com.tuankiet.sample.features.agents.interactor

import com.tuankiet.sample.core.interactor.UseCase
import com.tuankiet.sample.core.interactor.UseCase.None
import com.tuankiet.sample.features.agents.data.AgentsRepository

class GetAgents(
    private val repository: AgentsRepository
) : UseCase<List<Agent>, None>() {

    override suspend fun run(params: None) = repository.agents()
}
