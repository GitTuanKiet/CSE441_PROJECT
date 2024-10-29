package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.khanh.fragment.SplashFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SplashFragment())
                .commit()
        }
    }

    companion object {
        fun callingIntent(context: Context): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }
}