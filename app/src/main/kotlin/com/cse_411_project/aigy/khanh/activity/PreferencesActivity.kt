package com.cse_411_project.aigy.khanh.activity

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R

class PreferencesActivity : AppCompatActivity() {
    private lateinit var ibtnBack: ImageButton
    private lateinit var lbtnAccountInformation: LinearLayout
    private lateinit var lbtnChangePassword: LinearLayout
    private lateinit var lbtnInviteFriends: LinearLayout
    private lateinit var lbtnTheme: LinearLayout
    private lateinit var lbtnLanguage: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_preferences)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibtnBack = findViewById(R.id.ibtn_back)
        ibtnBack.setOnClickListener {
            finish()
        }

        lbtnAccountInformation = findViewById(R.id.lbtn_account_information)
        lbtnAccountInformation.setOnClickListener {
            val intent = Intent(
                this,
                com.cse_411_project.aigy.khanh.activity.AccountInformationActivity::class.java
            )
            startActivity(intent)
        }

        lbtnChangePassword = findViewById(R.id.lbtn_password)
        lbtnChangePassword.setOnClickListener {
            val intent = Intent(this, PasswordActivity::class.java)
            startActivity(intent)
        }

        lbtnInviteFriends = findViewById(R.id.lbtn_invite_friends)
        lbtnInviteFriends.setOnClickListener {
            val intent = Intent(this, InviteYourFriendActivity::class.java)
            startActivity(intent)
        }

        lbtnTheme = findViewById(R.id.lbtn_theme)
        lbtnTheme.setOnClickListener {
            val intent = Intent(this, ThemeActivity::class.java)
            startActivity(intent)
        }

        lbtnLanguage = findViewById(R.id.lbtn_language)
        lbtnLanguage.setOnClickListener {
            val intent = Intent(this, LanguageActivity::class.java)
            startActivity(intent)
        }
    }
}