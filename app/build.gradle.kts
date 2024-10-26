@file:Suppress("UnstableApiUsage")

import org.jetbrains.kotlin.gradle.dsl.JvmTarget


class AppConfig {
    val id = "com.tuankiet.sample"
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
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore.ktx)
    implementation(libs.firebase.firestore)
    implementation(libs.firebase.auth.ktx)
    implementation(libs.firebase.storage.ktx)
//    implementation(libs.firebase.database)

    // Jetpack compose dependencies
    // @see: https://developer.android.google.cn/develop/ui/compose/setup?hl=en#kotlin_1
    // Specify the Compose BOM with a version definition
    val composeBom = platform("androidx.compose:compose-bom:2024.06.00")
    implementation(composeBom)


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

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")



    // Unit/Integration tests dependencies
    testImplementation(composeBom)
    testImplementation(libs.kotest.runner.junit5)
    testImplementation(libs.kotest.assertions.core)
    testImplementation(libs.kotest.property)
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.robolectric)

    // UI tests dependencies
    androidTestImplementation(composeBom)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    androidTestImplementation(libs.androidx.runner)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.rules)
    androidTestImplementation(libs.androidx.espresso.intents)




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
