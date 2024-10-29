package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import com.cse_411_project.aigy.khanh.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignUp : TextView
    private lateinit var btnLogin : Button
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnBack : ImageButton
    private lateinit var txtForgetPassword : TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var fireStore: FirebaseFirestore
    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_in)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = FirebaseDatabase.getInstance().reference

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
            } else if (edtPassword.text.toString().isEmpty()) {
                edtPassword.error = "Vui lòng nhập mật khẩu"
                edtPassword.requestFocus()
            } else {
                firebaseAuth = FirebaseAuth.getInstance()
                firebaseAuth.signInWithEmailAndPassword(
                    edtEmail.text.toString(),
                    edtPassword.text.toString()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val userId = firebaseAuth.currentUser?.uid
                        if (userId != null) {
                            database.child("users").child(userId).get()
                                .addOnSuccessListener { dataSnapshot ->
                                    val user = dataSnapshot.getValue(User::class.java)
                                    if (user != null) {
                                        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
                                        sharedPreferences.edit().apply {
                                            putString("uid", user.uid)
                                            putString("decentralization", user.decentralization)
                                            putString("fullName", user.fullName)
                                            putString("email", user.email)
                                            putString("phoneNumber", user.phoneNumber)
                                            putString("urlImage", user.urlImage)
                                            putInt("referralCount", user.referralCount!!)
                                            putBoolean("online", user.online!!)
                                            putStringSet("conversationList", user.conversationList?.toSet())
                                            apply()
                                        }

                                        val updates = mutableMapOf<String, Any>()
                                        updates["online"] = true
                                        val userRef = user.uid?.let { FirebaseDatabase.getInstance().getReference("users").child(it) }

                                        if (userRef != null && updates.isNotEmpty()) {
                                            userRef.updateChildren(updates)
                                        }

                                        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                                        // Chuyển đến HomeActivity
//                                        val intent = Intent(this, HomeActivity::class.java)
//                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//                                        startActivity(intent)
//                                        finish()
                                    } else {
                                        Toast.makeText(this, "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                .addOnFailureListener { exception ->
                                    Toast.makeText(this, "Lỗi lấy thông tin: ${exception.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
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