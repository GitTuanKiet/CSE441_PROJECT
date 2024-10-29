package com.tuankiet.sample.features.admin.data.models

import com.tuankiet.sample.core.extension.empty

data class AgentModel(
    val id : String ,
    val user_id: String,
    val conversations : List<ConversationModel>
){
    companion object{
        val empty = AgentModel(String.empty(),String.empty(), emptyList())
    }
}
