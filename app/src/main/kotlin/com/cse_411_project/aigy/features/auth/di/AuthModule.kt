package com.cse_411_project.aigy.features.auth.di

import com.cse_411_project.aigy.features.auth.credentials.Authenticator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val authModule = module {
    singleOf(::Authenticator)
}
