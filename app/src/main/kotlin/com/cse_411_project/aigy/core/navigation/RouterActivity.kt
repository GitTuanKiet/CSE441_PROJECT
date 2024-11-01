package com.cse_411_project.aigy.core.navigation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cse_411_project.aigy.features.auth.credentials.Authenticator
import org.koin.android.ext.android.inject

class RouteActivity : AppCompatActivity() {

    private val sharedPreferences by lazy { getSharedPreferences("MyPrefs", Context.MODE_PRIVATE) }
    private val navigator: Navigator by lazy { Navigator(Authenticator(sharedPreferences)) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.showMain(this)
    }
}