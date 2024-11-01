package com.cse_411_project.aigy.features.chat.data

data class ChatRequest(
    val agentId: String,
    val knowledgeBaseId: String,
    val question: String,
    val sessionId: String
)