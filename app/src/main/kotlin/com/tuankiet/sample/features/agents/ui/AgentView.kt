package com.tuankiet.sample.features.agents.ui

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AgentView(val identifier: String, val author: String, val meta: AgentMetaView) : Parcelable

@Parcelize
data class AgentMetaView(val title: String, val description: String, val avatar: String, val tags: List<String>, val category: String) : Parcelable
