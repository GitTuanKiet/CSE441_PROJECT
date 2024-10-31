package com.cse_411_project.aigy.features.chat

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
private const val BASE_URL = "https://chat-agents.lobehub.com/"
private var retrofit: Retrofit? = null

fun getClient(): Retrofit {
    if (retrofit == null) {
        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
    return retrofit!!
}
}
