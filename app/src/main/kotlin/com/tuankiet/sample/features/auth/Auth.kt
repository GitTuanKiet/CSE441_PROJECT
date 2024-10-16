package com.tuankiet.sample.features.auth

import com.tuankiet.sample.core.Feature
import com.tuankiet.sample.features.auth.credentials.Authenticator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authFeature() = object : Feature {

    override fun name() = "auth"

    override fun diModule() = module {
        singleOf(::Authenticator)
    }
}
