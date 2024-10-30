package com.cse_411_project.aigy.khanh.model

import com.cse_411_project.aigy.core.extension.empty

data class UserModel(
    val uid: String,
    val fullName: String,
    val email: String,
    var password: String,
    var phoneNumber: String,
    val urlImage: String,
    val decentralization: String,
    val isOnline: Boolean,
    val idListAgent: List<String>,
    val conversationList: List<String>,
    val referralCount: Int,
){
    companion object {
        val empty = UserModel(
            uid = String.empty(),
            fullName = String.empty(),
            email = String.empty(),
            password = String.empty(),
            phoneNumber = String.empty(),
            urlImage = String.empty(),
            decentralization = String.empty(),
            isOnline = false,
            idListAgent = emptyList(),
            conversationList = emptyList(),
            referralCount = 0
        )
    }
}