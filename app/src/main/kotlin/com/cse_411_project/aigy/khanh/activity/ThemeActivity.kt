package com.cse_411_project.aigy.khanh.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R

class ThemeActivity : AppCompatActivity() {
    private lateinit var ibtnBack: ImageButton
    private lateinit var btnLight: Button
    private lateinit var btnDark: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_theme)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibtnBack = findViewById(R.id.ibtn_back)
        ibtnBack.setOnClickListener {
            finish()
        }

        btnLight = findViewById(R.id.btn_light_mode)
        btnLight.setOnClickListener {
            // Change theme to light
            sharedPreferences.edit().putBoolean("isDarkMode", false).apply()
        }

        btnDark = findViewById(R.id.btn_dark_mode)
        btnDark.setOnClickListener {
            // Change theme to dark
            sharedPreferences.edit().putBoolean("isDarkMode", true).apply()
        }
    }
}