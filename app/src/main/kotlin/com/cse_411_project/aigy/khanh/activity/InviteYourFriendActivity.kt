package com.cse_411_project.aigy.khanh.activity

import android.content.ClipboardManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R

class InviteYourFriendActivity : AppCompatActivity() {
    private lateinit var ibtnBack: ImageButton
    private lateinit var txtIDCode: TextView
    private lateinit var btnShare: Button
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_invite_your_friend)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibtnBack = findViewById(R.id.ibtn_back)
        txtIDCode = findViewById(R.id.txt_id_code)
        btnShare = findViewById(R.id.btn_share)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val idCode = sharedPreferences.getString("uid", "")
        txtIDCode.text = idCode

        ibtnBack.setOnClickListener {
            finish()
        }

        btnShare.setOnClickListener {
            val storeUrl = "https://example.com/store"
            val message =
                "Mời bạn tham gia cửa hàng của tôi! URL: $storeUrl?ref=$idCode. Đã sao chép vào clipboard."

            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = android.content.ClipData.newPlainText("store_link", message)
            clipboard.setPrimaryClip(clip)

            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }
}