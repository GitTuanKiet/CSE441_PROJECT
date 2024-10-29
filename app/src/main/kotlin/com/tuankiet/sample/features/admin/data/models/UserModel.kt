package com.tuankiet.sample.features.admin.data.models

import com.tuankiet.sample.core.extension.empty

data class UserModel(
    val uid: String,
    val name: String,
    val email: String,
    val phone: String,
    val urlImg: String,
    val decentralization: String,
    val isOnline: Boolean,
    val idListAgent: List<String>,
    val listVist : List<Long>
    ){
    companion object {
        val empty = UserModel(String.empty(),String.empty(),String.empty(),String.empty(),String.empty(),String.empty(),false, emptyList() , emptyList())
    }
}