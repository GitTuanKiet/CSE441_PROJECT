package com.tuankiet.sample.features.agents.data

import retrofit2.Call
import retrofit2.Retrofit

class AgentsService(retrofit: Retrofit) : AgentsApi {
    private val agentsApi by lazy { retrofit.create(AgentsApi::class.java) }

    override fun requestAgents() = agentsApi.requestAgents()
}