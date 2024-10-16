package com.tuankiet.sample.features.agents.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

internal interface AgentsApi {
    companion object {
        private const val PARAM_AGENT_ID = "agentId"
        private const val AGENTS = "agents.json"
        private const val AGENT_DETAILS = "agent_0{$PARAM_AGENT_ID}.json"
    }

    @GET("/")
    fun requestAgents(): Call<ResponseEntity>
}