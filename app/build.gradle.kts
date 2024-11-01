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
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.google.gms.google.services)
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
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

        viewBinding = true
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
    implementation(libs.androidx.activity)
//    implementation(libs.firebase.database)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.gridlayout)
    implementation(libs.google.generativeai)
    runtimeOnly(libs.androidx.viewpager2)


    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation (libs.androidx.runtime)

    // Page indicator
    implementation(libs.dotsindicator)

    // Firebase
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.database.ktx)
    implementation(libs.firebase.firestore.ktx.v2410)
    implementation(libs.firebase.storage.ktx)

    // Specify Compose library dependencies without a version definition
    implementation("androidx.compose.foundation:foundation")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-core")

    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    // Integration with activities
    implementation("androidx.activity:activity-compose:1.9.0")
    // Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1")
    // Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    // ..
    // ..
    // Gson
    implementation(libs.gson)

    // Image Cropper
    implementation("com.vanniktech:android-image-cropper:4.6.0")

    // Image Picker
    implementation("com.github.esafirm:android-image-picker:2.0.0")
    implementation("com.google.android.gms:play-services-cast-framework:22.0.0")



    // Unit/Integration tests dependencies
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)





    // Development/Tooling dependencies
//    debugImplementation(libs.leakcanary.android)
    // UI Chart
    implementation ("com.github.PhilJay:MPAndroidChart:v3.1.0")
    implementation(platform("com.google.firebase:firebase-bom:33.4.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("de.hdodenhof:circleimageview:3.1.0")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
}
