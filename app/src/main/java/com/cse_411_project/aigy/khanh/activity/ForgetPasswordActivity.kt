package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R

class ForgetPasswordActivity : AppCompatActivity() {
    private lateinit var iBtnBack : ImageButton
    private lateinit var lBtnEmailRecovery : LinearLayout
    private lateinit var btnNext : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_forget_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        iBtnBack = findViewById(R.id.ibtn_back)

        iBtnBack.setOnClickListener {
            finish()
        }

        lBtnEmailRecovery = findViewById(R.id.lbtn_email_recovery)

        lBtnEmailRecovery.setOnClickListener {
            lBtnEmailRecovery.isSelected = !lBtnEmailRecovery.isSelected
        }

        btnNext = findViewById(R.id.btn_next)

        btnNext.setOnClickListener {
            if (lBtnEmailRecovery.isSelected) openEmailRecoveryActivity()
        }
    }

    private fun openEmailRecoveryActivity() {
        val intent = PhoneNumberRecoveryActivity.newIntent(this)
        startActivity(intent)
    }


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ForgetPasswordActivity::class.java)
        }
    }
}