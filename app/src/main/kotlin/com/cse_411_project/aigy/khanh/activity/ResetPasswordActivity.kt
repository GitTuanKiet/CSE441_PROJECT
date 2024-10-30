package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.khanh.repositories.UserRepository
import com.cse_411_project.aigy.khanh.viewmodel.UserViewModel

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var phoneNumber: String
    private lateinit var email: String

    private val userRepository = UserRepository()
    private val userViewModel = UserViewModel(userRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Nhận số điện thoại từ Intent
        phoneNumber = intent.getStringExtra("phone_number") ?: ""
        email = intent.getStringExtra("email") ?: ""

        this.edtPassword = findViewById(R.id.et_password)
        this.edtConfirmPassword = findViewById(R.id.et_confirm_password)
        this.btnSaveChanges = findViewById(R.id.btn_save_changes)

        this.btnSaveChanges.setOnClickListener {
            val password = this.edtPassword.text.toString()
            val confirmPassword = this.edtConfirmPassword.text.toString()

            if (password.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            resetPassword(password)
        }
    }

    private fun resetPassword(newPassword: String) {
        userViewModel.updatePasswordByEmailAndPhoneNumber(email, phoneNumber, newPassword)
    }

    companion object {
        fun newIntent(context: Context, email: String, phoneNumber: String): Intent {
            val intent = Intent(context, ResetPasswordActivity::class.java)
            intent.putExtra("phone_number", phoneNumber)
            intent.putExtra("email", email)
            return intent
        }
    }
}
