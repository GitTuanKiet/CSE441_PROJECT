package com.cse_411_project.aigy.features.chat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.cse_411_project.aigy.R

class WelcomeAgentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_agent)

        val backButton: ImageButton = findViewById(R.id.back_button)
        backButton.setOnClickListener {
            finish()
        }

        val getStartedButton: Button = findViewById(R.id.get_started_button)
        getStartedButton.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        @JvmStatic
        fun callingIntent(context: Context): Intent {
            return Intent(context, WelcomeAgentActivity::class.java)
        }
    }
}