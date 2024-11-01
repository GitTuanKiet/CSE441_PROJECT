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
import com.cse_411_project.aigy.khanh.model.UserModel
import com.cse_411_project.aigy.khanh.repositories.UserRepository
import com.cse_411_project.aigy.khanh.viewmodel.UserViewModel

class PasswordActivity : AppCompatActivity() {
    private lateinit var edtCurrentPassword: EditText
    private lateinit var edtNewPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var ibtnBack: ImageButton

    private val userRepository = UserRepository()
    private val userViewModel = UserViewModel(userRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_password)

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ibtnBack = findViewById(R.id.ibtn_back)
        ibtnBack.setOnClickListener { finish() }

        edtCurrentPassword = findViewById(R.id.et_current_password)
        edtNewPassword = findViewById(R.id.et_new_password)
        edtConfirmPassword = findViewById(R.id.et_confirm_password)
        btnSaveChanges = findViewById(R.id.btn_save_changes)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        btnSaveChanges.setOnClickListener {
            val currentPassword = edtCurrentPassword.text.toString()
            val newPassword = edtNewPassword.text.toString()
            val confirmPassword = edtConfirmPassword.text.toString()

            // Validate input fields
            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else if (newPassword != confirmPassword) {
                Toast.makeText(this, "New password and confirm password do not match", Toast.LENGTH_SHORT).show()
            } else {
                val uid = sharedPreferences.getString("uid", "")

                // Create a UserModel object with the new password
                val updatedUser = UserModel(
                    uid = uid ?: "",
                    password = newPassword,
                    fullName = sharedPreferences.getString("fullName", "") ?: "",
                    email = sharedPreferences.getString("email", "") ?: "",
                    phoneNumber = sharedPreferences.getString("phone_number", "") ?: "",
                    urlImage = sharedPreferences.getString("url_image", "") ?: "",
                    decentralization = sharedPreferences.getString("decentralization", "") ?: "",
                    isOnline = sharedPreferences.getBoolean("is_online", false),
                    idListAgent = sharedPreferences.getStringSet("id_list_agent", setOf())?.toList() ?: emptyList(),
                    conversationList = sharedPreferences.getStringSet("conversation_list", setOf())?.toList() ?: emptyList(),
                    referralCount = sharedPreferences.getInt("referral_count", 0)
                )

                // Call ViewModel to update the user
                    userViewModel.updateUser(updatedUser) { isSuccess ->
                        if (isSuccess) {
                            sharedPreferences.edit().putString("password", newPassword).apply()
                            Toast.makeText(this, "Cập nhật mật khẩu thành công!", Toast.LENGTH_SHORT).show()
                            return@updateUser
                        } else {
                            Toast.makeText(this, "Cập nhật mật khẩu thất bại!", Toast.LENGTH_SHORT).show()
                            return@updateUser
                        }

                    }

            }
        }
    }
}

