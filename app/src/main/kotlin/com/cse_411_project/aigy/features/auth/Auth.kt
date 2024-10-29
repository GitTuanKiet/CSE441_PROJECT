package com.cse_411_project.aigy.features.auth

import com.cse_411_project.aigy.core.Feature
import com.cse_411_project.aigy.features.auth.credentials.Authenticator
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun authFeature() = object : Feature {

    override fun name() = "auth"

    override fun diModule() = module {
        singleOf(::Authenticator)
    }
}
