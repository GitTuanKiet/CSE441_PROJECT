package com.cse_411_project.aigy.features.agents.data

import com.cse_411_project.aigy.features.agents.interactor.Agent
import com.cse_411_project.aigy.features.agents.interactor.AgentConfig
import com.cse_411_project.aigy.features.agents.interactor.AgentDetails
import com.cse_411_project.aigy.features.agents.interactor.AgentMeta

data class AgentStoreEntity(
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

data class AgentDetailsEntity (
    private val identifier: String,
    private val author: String,
    private val config: AgentConfigEntity,
    private val meta: AgentMetaEntity
) {
    fun toAgentDetails() = AgentDetails(identifier, author, config.toAgentConfig(), meta.toAgentMeta())
}

data class AgentConfigEntity(
    private val systemRoles: String,
) {
    fun toAgentConfig() = AgentConfig(systemRoles)
}