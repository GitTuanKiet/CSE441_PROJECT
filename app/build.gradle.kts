@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget


class AppConfig {
    val id = "com.cse_411_project.aigy"
    val versionCode = 2
    val versionName = "2.0"

    val compileSdk = libs.versions.compileSdk.get().toInt()
    val minSdk = libs.versions.minSdk.get().toInt()
    val targetSdk = libs.versions.targetSdk.get().toInt()

    val jvmTarget = JvmTarget.JVM_17
    val jvmToolChain = 17
    val javaVersion = JavaVersion.VERSION_17
    val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose.compiler)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.google.gms.google.services)
}

val appConfig = AppConfig()

android {
    namespace = appConfig.id
    compileSdk = appConfig.compileSdk

    defaultConfig {
        applicationId = appConfig.id

        minSdk = appConfig.minSdk
        targetSdk = appConfig.targetSdk
        versionCode = appConfig.versionCode
        versionName = appConfig.versionName

        testInstrumentationRunner = appConfig.testInstrumentationRunner
    }

    compileOptions {
        sourceCompatibility = appConfig.javaVersion
        targetCompatibility = appConfig.javaVersion
    }

    buildFeatures {
        // Enables Compose functionality in Android Studio.
        // @see: https://developer.android.com/develop/ui/compose/tooling#bom
        // @see: https://developer.android.com/develop/ui/compose/bom
        compose = true
        buildConfig = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
        }
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}

kotlin {
    jvmToolchain(appConfig.jvmToolChain)
    compilerOptions {
        jvmTarget.set(appConfig.jvmTarget)
    }
}

composeCompiler {
    enableStrongSkippingMode = true
}

dependencies {
    // Application dependencies
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.koin.android)
    implementation(libs.android.appcompat)
    implementation(libs.converter.gson)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    implementation(libs.androidx.viewpager2)
    implementation(libs.material)
    implementation(libs.androidx.activity)

    // Page indicator
    implementation(libs.dotsindicator)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.gson)
    implementation(libs.firebase.database.ktx)
}
