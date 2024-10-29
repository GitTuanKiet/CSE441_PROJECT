package com.tuankiet.sample.features.admin.data.models

import com.tuankiet.sample.core.extension.empty

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

