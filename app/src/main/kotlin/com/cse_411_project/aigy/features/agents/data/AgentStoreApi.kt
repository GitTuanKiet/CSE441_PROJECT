package com.cse_411_project.aigy.features.agents.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

internal interface AgentStoreApi {
    companion object {
        private const val PARAM_AGENT_IDENTIFIER = "identifier"
        private const val AGENT_STORE = "/"
        private const val AGENT_DETAILS = "/{$PARAM_AGENT_IDENTIFIER}.json"
    }

    @GET(AGENT_STORE)
    fun agentStore(): Call<AgentStoreEntity>

    @GET(AGENT_DETAILS)
    fun agentDetails(@Path(PARAM_AGENT_IDENTIFIER) identifier: String): Call<AgentDetailsEntity>
}