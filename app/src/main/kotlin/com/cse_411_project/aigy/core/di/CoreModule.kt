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

