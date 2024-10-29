package com.tuankiet.sample.features.agents.data

import retrofit2.Call
import retrofit2.Retrofit

class AgentStoreService(retrofit: Retrofit) : AgentStoreApi {
    private val agentStoreApi by lazy { retrofit.create(AgentStoreApi::class.java) }

    override fun agentStore() = agentStoreApi.agentStore()
    override fun agentDetails(identifier: String) = agentStoreApi.agentDetails(identifier)
}