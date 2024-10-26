package com.tuankiet.sample.features.admin.data.models

import com.tuankiet.sample.core.extension.empty

data class DateModel (
    val date : String,
    val count : Long
){
    companion object{
        val empty = DateModel(String.empty() , 0)
    }
}
