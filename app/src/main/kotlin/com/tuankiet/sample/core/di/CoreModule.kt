package com.tuankiet.sample.core.di

import com.google.gson.GsonBuilder
import com.tuankiet.sample.core.navigation.Navigator
import com.tuankiet.sample.core.network.NetworkHandler
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

