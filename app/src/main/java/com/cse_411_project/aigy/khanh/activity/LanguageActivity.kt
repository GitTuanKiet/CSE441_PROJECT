package com.cse_411_project.aigy.khanh.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import android.content.Context
import java.util.Locale

class LanguageActivity : AppCompatActivity() {
    private lateinit var btn_english_mode : Button
    private lateinit var btn_vienamese_mode : Button
    private lateinit var sharedPreferences: SharedPreferences
    private val LANGUAGE_KEY = "language_key"
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val language = sharedPreferences.getString(LANGUAGE_KEY, "en")
        
        setContentView(R.layout.activity_language)

        btn_english_mode = findViewById(R.id.btn_english_mode)
        btn_vienamese_mode = findViewById(R.id.btn_vietnamese_mode)

        btn_english_mode.setOnClickListener{
            changeLanguage("en")
        }
        btn_vienamese_mode.setOnClickListener{
            changeLanguage("vi")
        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

    }
    fun setLocale(localeName: String) {
        val locale = Locale(localeName)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
        recreate()
    }
    private fun changeLanguage(newLanguage : String) {
        val currentLanguage = sharedPreferences.getString(LANGUAGE_KEY, "en")
        if(currentLanguage != newLanguage){
            sharedPreferences.edit().putString(LANGUAGE_KEY, newLanguage).apply()
            setLocale(newLanguage)
        }

    }
}