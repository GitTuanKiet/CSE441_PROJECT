package com.tuankiet.sample.features.agents

import com.tuankiet.sample.core.Feature
import com.tuankiet.sample.features.agents.data.AgentStoreRepository
import com.tuankiet.sample.features.agents.data.AgentStoreService
import com.tuankiet.sample.features.agents.interactor.GetAgentDetails
import com.tuankiet.sample.features.agents.interactor.GetAgents
import com.tuankiet.sample.features.agents.ui.AgentDetailsAnimator
import com.tuankiet.sample.features.agents.ui.AgentDetailsViewModel
import com.tuankiet.sample.features.agents.ui.AgentsViewModel
import com.tuankiet.sample.features.agents.ui.AgentsAdapter
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

fun agentsFeature() = object : Feature {

    override fun name() = "agents"

    override fun diModule() = module {
        // Agents Feature
        factoryOf(::AgentStoreService)
        factoryOf(::AgentsAdapter)
        factory { AgentStoreRepository.Network(get(), get()) } bind AgentStoreRepository::class
        // Agents
        viewModelOf(::AgentsViewModel)
        factoryOf(::GetAgents)
        // Agent Details
        viewModelOf(::AgentDetailsViewModel)
        factoryOf(::GetAgentDetails)
        factoryOf(::AgentDetailsAnimator)
    }
}
