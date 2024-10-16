package com.tuankiet.sample.features.agents.data

import com.tuankiet.sample.features.agents.interactor.Agent
import com.tuankiet.sample.features.agents.interactor.AgentMeta

data class ResponseEntity(
    private val agents: List<AgentEntity>,
    private val tags: List<String>
) {
    fun toAgents() = agents.map { it.toAgent() }
    fun toCategories() = tags
}

data class AgentEntity(
    private val identifier: String,
    private val author: String,
    private val meta: AgentMetaEntity
) {
    fun toAgent() = Agent(identifier, author, meta.toAgentMeta())
}

data class AgentMetaEntity(
    private val title: String,
    private val description: String,
    private val avatar: String,
    private val tags: List<String>,
    private val category: String
) {
    fun toAgentMeta() = AgentMeta(title, description, avatar, tags, category)
}