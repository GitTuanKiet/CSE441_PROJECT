package com.tuankiet.sample.features.admin.data

import com.tuankiet.sample.core.extension.empty

data class UserModel (
    val UID : String ,
    val Name : String,
    val Email : String,
    val Phone : String,
    val UrlImg : String,
    val Decentralization : String,
    val IsOnline : Boolean,
    val IdListAgent : List<String>
    ){
    companion object {
        val empty = UserModel(String.empty(),String.empty(),String.empty(),String.empty(),String.empty(),String.empty(),false, emptyList())
    }
}