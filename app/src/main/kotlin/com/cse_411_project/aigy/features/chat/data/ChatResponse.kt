package com.cse_411_project.aigy.features.chat.data

import com.google.gson.annotations.SerializedName

data class ChatResponse(
    @SerializedName("data")val data: Data,
    val status: String
) {
    data class Data(
        @SerializedName("answer")val answer: String,
        val suggestedQuestions: List<String>,
        var isDisplayed: Boolean = false
    )
}