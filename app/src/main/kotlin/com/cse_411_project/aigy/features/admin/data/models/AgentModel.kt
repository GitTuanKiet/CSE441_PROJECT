package com.cse_411_project.aigy.features.admin.data.models

import com.cse_411_project.aigy.core.extension.empty

data class AgentModel(
    val id : String ,
    val user_id: String,
    val conversations : List<ConversationModel>
){
    companion object{
        val empty = AgentModel(String.empty(),String.empty(), emptyList())
    }
}
