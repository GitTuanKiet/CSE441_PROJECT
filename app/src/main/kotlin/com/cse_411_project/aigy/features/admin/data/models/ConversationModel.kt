package com.cse_411_project.aigy.features.admin.data.models

import com.cse_411_project.aigy.core.extension.empty

data class ConversationModel(
    val id : String ,
    val user_id: String,
    val agent_id : String ,
    val messages : List<MessageModel>
){
    companion object{
        val empty = ConversationModel(String.empty(),String.empty(),String.empty(), emptyList())
    }
}

