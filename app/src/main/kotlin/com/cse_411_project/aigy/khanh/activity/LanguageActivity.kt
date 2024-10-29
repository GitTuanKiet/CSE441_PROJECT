package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import java.util.Locale

class LanguageActivity : AppCompatActivity() {
    private lateinit var btnEnglishMode: Button
    private lateinit var btnVietnameseMode: Button
    private lateinit var sharedPreferences: SharedPreferences
    private val LANGUAGE_KEY = "language_key"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val savedLanguage = sharedPreferences.getString(LANGUAGE_KEY, "en") ?: "en"
        setLocale(savedLanguage)

        setContentView(R.layout.activity_language)
        btnEnglishMode = findViewById(R.id.btn_english_mode)
        btnVietnameseMode = findViewById(R.id.btn_vietnamese_mode)

        btnEnglishMode.setOnClickListener {
            changeLanguage("en")
        }

        btnVietnameseMode.setOnClickListener {
            changeLanguage("vi")
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)

        recreate()
    }

    private fun changeLanguage(newLanguage: String) {
        val currentLanguage = sharedPreferences.getString(LANGUAGE_KEY, "en")
        if (currentLanguage != newLanguage) {
            sharedPreferences.edit().putString(LANGUAGE_KEY, newLanguage).apply()
            setLocale(newLanguage)
        }
    }
}