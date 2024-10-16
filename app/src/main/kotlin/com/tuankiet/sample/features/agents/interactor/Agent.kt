package com.tuankiet.sample.features.agents.interactor

import com.tuankiet.sample.core.extension.empty

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