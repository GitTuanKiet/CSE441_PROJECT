package com.cse_411_project.aigy.features.agents.interactor

import com.cse_411_project.aigy.core.extension.empty

data class Agent(
    val identifier: String,
    val author: String,
    val meta: AgentMeta
) {

    companion object {
        val empty = Agent(String.empty(), String.empty(), AgentMeta.empty)
    }
}

data class AgentMeta(
    val title: String,
    val description: String,
    val avatar: String,
    val tags: List<String>,
    val category: String
) {

    companion object {
        val empty = AgentMeta(String.empty(), String.empty(), String.empty(), emptyList(), String.empty())
    }
}

data class AgentDetails(
    val identifier: String,
    val author: String,
    val config: AgentConfig,
    val meta: AgentMeta
) {
    companion object {
        val empty = AgentDetails(String.empty(), String.empty(), AgentConfig.empty, AgentMeta.empty)
    }
}

data class AgentConfig(
    val systemRole: String
) {
    companion object {
        val empty = AgentConfig(String.empty())
    }
}