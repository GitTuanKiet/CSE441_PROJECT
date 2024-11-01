package com.cse_411_project.aigy.features.chat.data

import okhttp3.MultipartBody
import okio.Timeout
import retrofit2.Response
import retrofit2.http.Body


import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface WebApi {
    @POST("api/legacy/runnable/invoke")
    suspend fun chatWithBot(
        @Header("Authorization") accessToken: String,
        @Header("x-api-key") apiKey: String,
        @Body chatRequest: ChatRequest
    ): Response<ChatResponse>
}