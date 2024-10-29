package com.cse_411_project.aigy.features.agents.data

import retrofit2.Call
import retrofit2.http.GET

internal interface AgentStoreApi {
    companion object {
        private const val AGENT_IDENTIFIER = "identifier"
        private const val AGENT_STORE = "/"
        private const val AGENT_DETAILS = "{$AGENT_IDENTIFIER}.json"
    }

    @GET(AGENT_STORE)
    fun agentStore(): Call<AgentStoreEntity>

    @GET(AGENT_DETAILS)
    fun agentDetails(identifier: String): Call<AgentDetailsEntity>
}