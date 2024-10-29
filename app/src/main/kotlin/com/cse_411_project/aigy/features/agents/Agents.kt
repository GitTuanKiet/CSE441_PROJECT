package com.cse_411_project.aigy.features.agents

import com.cse_411_project.aigy.core.Feature
import com.cse_411_project.aigy.features.agents.data.AgentStoreRepository
import com.cse_411_project.aigy.features.agents.data.AgentStoreService
import com.cse_411_project.aigy.features.agents.interactor.GetAgentDetails
import com.cse_411_project.aigy.features.agents.interactor.GetAgents
import com.cse_411_project.aigy.features.agents.ui.AgentDetailsAnimator
import com.cse_411_project.aigy.features.agents.ui.AgentDetailsViewModel
import com.cse_411_project.aigy.features.agents.ui.AgentsViewModel
import com.cse_411_project.aigy.features.agents.ui.AgentsAdapter
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
