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
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_reset_password)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Khởi tạo FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        // Nhận số điện thoại từ Intent
        phoneNumber = intent.getStringExtra("phone_number") ?: ""

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
        firebaseAuth.fetchSignInMethodsForEmail(phoneNumber)
            .addOnCompleteListener { task ->
                if (task.isSuccessful && task.result?.signInMethods?.isNotEmpty() == true) {
                    val user = firebaseAuth.currentUser

                    user?.updatePassword(newPassword)?.addOnCompleteListener { updateTask ->
                        if (updateTask.isSuccessful) {
                            Toast.makeText(this, "Mật khẩu đã được thay đổi thành công", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "Cập nhật mật khẩu thất bại: ${updateTask.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Không tìm thấy tài khoản với số điện thoại này", Toast.LENGTH_SHORT).show()
                }
            }
    }


    companion object {
        fun newIntent(context: Context, phoneNumber: String): Intent {
            val intent = Intent(context, ResetPasswordActivity::class.java)
            intent.putExtra("phone_number", phoneNumber)
            return intent
        }
    }
}
