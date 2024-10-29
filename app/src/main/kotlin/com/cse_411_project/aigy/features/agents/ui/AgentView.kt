package com.cse_411_project.aigy.features.agents.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentView(val identifier: String, val author: String, val meta: AgentMetaView) : Parcelable

@Parcelize
data class AgentMetaView(val title: String, val description: String, val avatar: String, val tags: List<String>, val category: String) : Parcelable

@Parcelize
data class AgentDetailsView(val identifier: String, val author: String, val config: AgentConfigView, val meta: AgentMetaView) : Parcelable

@Parcelize
data class AgentConfigView(val systemRoles: String) : Parcelable