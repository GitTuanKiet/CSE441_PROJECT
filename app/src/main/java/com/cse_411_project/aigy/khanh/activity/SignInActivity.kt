package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignUp : TextView
    private lateinit var btnLogin : Button
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnBack : ImageButton
    private lateinit var txtForgetPassword : TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        this.btnSignUp = findViewById(R.id.txt_sign_in)

        this.btnSignUp.setOnClickListener {
            openSignUpActivity()
        }

        this.btnBack = findViewById(R.id.ibtn_back)

        this.btnBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        this.txtForgetPassword = findViewById(R.id.txt_forget_password)

        this.txtForgetPassword.setOnClickListener {
            openForgetPasswordActivity()
        }

        this.btnLogin = findViewById(R.id.btn_login)
        this.btnLogin.setOnClickListener {
            if (edtEmail.text.toString().isEmpty()) {
                edtEmail.error = "Vui lòng nhập email"
                edtEmail.requestFocus()
                Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show()
            } else if (edtPassword.text.toString().isEmpty()) {
                edtPassword.error = "Vui lòng nhập mật khẩu"
                edtPassword.requestFocus()
                Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show()
            } else {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signInWithEmailAndPassword(edtEmail.text.toString(), edtPassword.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {

//                            TODO: Open HomeActivity
//                            val intent = Intent(this, HomeActivity::class.java)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                            startActivity(intent)
//                            finish()

                        } else {
                            edtEmail.error = "Email hoặc mật khẩu không đúng"
                            edtEmail.requestFocus()
                        }
                    }
            }
        }
    }

    private fun openSignUpActivity() {
        val intent = SignUpActivity.newIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun openForgetPasswordActivity() {
        val intent = ForgetPasswordActivity.newIntent(this)
        startActivity(intent)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }
}