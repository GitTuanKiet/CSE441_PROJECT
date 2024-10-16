package com.tuankiet.sample.features.agents

import com.tuankiet.sample.core.Feature
import com.tuankiet.sample.features.agents.data.AgentsRepository
import com.tuankiet.sample.features.agents.data.AgentsService
//import com.tuankiet.sample.features.agents.interactor.GetMovieDetails
import com.tuankiet.sample.features.agents.interactor.GetAgents
//import com.tuankiet.sample.features.agents.interactor.PlayMovie
//import com.tuankiet.sample.features.agents.ui.MovieDetailsViewModel
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
        factoryOf(::AgentsService)
        factoryOf(::AgentsAdapter)
        factory { AgentsRepository.Network(get(), get()) } bind AgentsRepository::class
        // Agents
        viewModelOf(::AgentsViewModel)
        factoryOf(::GetAgents)
        // Movie Details
//        viewModelOf(::MovieDetailsViewModel)
//        factoryOf(::GetMovieDetails)
//        factoryOf(::PlayMovie)
    }
}
