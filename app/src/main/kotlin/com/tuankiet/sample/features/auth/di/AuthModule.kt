package com.tuankiet.sample.features.auth.di

import com.tuankiet.sample.core.navigation.Navigator
import com.tuankiet.sample.core.network.NetworkHandler
import com.tuankiet.sample.features.auth.credentials.Authenticator
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val authModule = module {
    singleOf(::Authenticator)
}
