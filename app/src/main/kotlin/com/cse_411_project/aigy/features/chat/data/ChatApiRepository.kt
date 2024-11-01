package com.cse_411_project.aigy.features.chat.data

import android.util.Log
import com.cse_411_project.aigy.core.di.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ChatApiRepository {
    private val chatBotApi: WebApi = RetrofitHelper.getChatBotInstance().create(WebApi::class.java)

    suspend fun chatWithBot(accessToken: String, apiKey: String, chatRequest: ChatRequest): Result<ChatResponse> {
        return  withContext(Dispatchers.IO) {
            val messageResponse = chatBotApi.chatWithBot(accessToken, apiKey, chatRequest)
            if(messageResponse.isSuccessful){
                Log.d("WebApiRepositoryImp", "Raw Response Body: ${messageResponse.body()}")
                Result.Success(messageResponse.body()!!)
            }else{
                Log.d("WebApiRepositoryImp", "Error: ${messageResponse.message()}")
                Result.Error(Exception(messageResponse.message()))
            }
        }
    }
}