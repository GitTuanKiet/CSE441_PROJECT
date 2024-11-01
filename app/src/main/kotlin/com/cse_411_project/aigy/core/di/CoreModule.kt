package com.cse_411_project.aigy.core.di

import com.google.gson.GsonBuilder
import com.cse_411_project.aigy.core.navigation.Navigator
import com.cse_411_project.aigy.core.network.NetworkHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val coreModule = module {
    singleOf(::retrofit)
    singleOf(::NetworkHandler)
    singleOf(::Navigator)
}

private fun retrofit(): Retrofit {
    val gson = GsonBuilder()
        .disableHtmlEscaping()
        .create()

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    val client = OkHttpClient
        .Builder()
        .addInterceptor(logging)
        .build()

    return Retrofit.Builder()
        .baseUrl("https://chat-agents.lobehub.com/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}


object RetrofitHelper {
    private const val BASE_URL = "http://146.190.136.216:3005/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

    private val chatBotClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()


    fun getChatBotInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(chatBotClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}