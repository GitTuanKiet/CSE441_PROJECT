package com.cse_411_project.aigy.khanh.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import com.google.firebase.database.FirebaseDatabase

class PasswordActivity : AppCompatActivity() {
    private lateinit var edtCurrentPassword: EditText
    private lateinit var edtNewPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ibtnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibtnBack = findViewById(R.id.ibtn_back)
        ibtnBack.setOnClickListener {
            finish()
        }

        edtCurrentPassword = findViewById(R.id.et_current_password)
        edtNewPassword = findViewById(R.id.et_new_password)
        edtConfirmPassword = findViewById(R.id.et_confirm_password)
        btnSaveChanges = findViewById(R.id.btn_save_changes)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        btnSaveChanges.setOnClickListener {
            val currentPassword = edtCurrentPassword.text.toString()
            val newPassword = edtNewPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show()
            } else {
                val uid = sharedPreferences.getString("uid", "")
                val userRef = uid?.let { FirebaseDatabase.getInstance().getReference("users").child(it) }
                val updates = mutableMapOf<String, Any>()
                updates["password"] = newPassword

                if (userRef != null && updates.isNotEmpty()) {
                    userRef.updateChildren(updates)
                        .addOnSuccessListener {
                        Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener { exception ->
                            Toast.makeText(this, "Lỗi cập nhật thông tin: ${exception.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
