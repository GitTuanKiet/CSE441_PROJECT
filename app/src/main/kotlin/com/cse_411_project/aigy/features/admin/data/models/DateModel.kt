package com.cse_411_project.aigy.features.admin.data.models

import com.cse_411_project.aigy.core.extension.empty

data class DateModel (
    val date : String,
    val count : Long
){
    companion object{
        val empty = DateModel(String.empty() , 0)
    }
}
