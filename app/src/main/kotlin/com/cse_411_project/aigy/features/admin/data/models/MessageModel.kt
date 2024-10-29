package com.cse_411_project.aigy.features.admin.data.models

import com.cse_411_project.aigy.core.extension.empty

data class MessageModel(
    val id: String,
    val senderId: String,
    val content: String,
    val type: String,
    val completionTime : Long,
    val timestamp: Long
){
    companion object{
        val empty = MessageModel(String.empty(),String.empty(),String.empty(), String.empty() , 0 ,0 )
    }
}
