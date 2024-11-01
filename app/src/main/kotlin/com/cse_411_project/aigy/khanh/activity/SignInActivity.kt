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
import androidx.appcompat.app.AppCompatActivity
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.features.chat.ExploreActivity
import com.cse_411_project.aigy.khanh.model.UserModel
import com.cse_411_project.aigy.khanh.repositories.UserRepository
import com.cse_411_project.aigy.khanh.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var btnSignUp: TextView
    private lateinit var btnLogin: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnBack: ImageButton
    private lateinit var txtForgetPassword: TextView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        initFirebaseAuth()
        initSharedPreferences()
        initViewModel()
        initViews()
        setupClickListeners()
    }

    private fun initFirebaseAuth() {
        firebaseAuth = FirebaseAuth.getInstance()
    }

    private fun initSharedPreferences() {
        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    }

    private fun initViewModel() {
        userViewModel = UserViewModel(UserRepository())
    }

    private fun initViews() {
        btnSignUp = findViewById(R.id.txt_sign_in)
        btnBack = findViewById(R.id.ibtn_back)
        txtForgetPassword = findViewById(R.id.txt_forget_password)
        edtEmail = findViewById(R.id.et_email)
        edtPassword = findViewById(R.id.et_password)
        btnLogin = findViewById(R.id.btn_login)
    }

    private fun setupClickListeners() {
        btnSignUp.setOnClickListener { openSignUpActivity() }
        btnBack.setOnClickListener { finish() }
        txtForgetPassword.setOnClickListener { openForgetPasswordActivity() }

        btnLogin.setOnClickListener { handleLogin() }
    }

    private fun handleLogin() {
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()

        if (validateInput(email, password)) {
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        firebaseAuth.currentUser?.uid?.let { userId ->
                            showToast("Đang đăng nhập...")
                            userViewModel.getUserByUID(userId) { userModel ->
                                showToast("Đã tìm thấy người dùng")
                                if (userModel != null) {
                                    saveUserDataToPreferences(userModel)
                                    showToast("Đăng nhập thành công")
//                                    startHomeActivity()

                                    openProfileActivity()
                                } else {
                                    showToast("Không tìm thấy người dùng")
                                }
                            }
                        }
                    } else {
                        showToast("Email hoặc mật khẩu không đúng")
                    }
                }
        }
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isEmpty().also { if (it) edtEmail.error = "Vui lòng nhập email" } -> false
            password.isEmpty().also { if (it) edtPassword.error = "Vui lòng nhập mật khẩu" } -> false
            else -> true
        }
    }

    private fun saveUserDataToPreferences(userModel: UserModel) {
        with(sharedPreferences.edit()) {
            putString("uid", userModel.uid)
            putString("decentralization", userModel.decentralization)
            putString("fullName", userModel.fullName)
            putString("password", userModel.password)
            putString("email", userModel.email)
            putString("phoneNumber", userModel.phoneNumber)
            putString("urlImage", userModel.urlImage)
            putInt("referralCount", userModel.referralCount)
            putBoolean("isOnline", true)
            putStringSet("idListAgent", userModel.idListAgent.toSet())
            putStringSet("conversationList", userModel.conversationList.toSet())
            apply()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun startHomeActivity() {
        val intent = Intent(this, ExploreActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }

    private fun openProfileActivity() {
        startActivity(ProfileActivity.newIntent(this))
    }

    private fun openSignUpActivity() {
        startActivity(SignUpActivity.newIntent(this))
    }

    private fun openForgetPasswordActivity() {
        startActivity(ForgetPasswordActivity.newIntent(this))
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignInActivity::class.java)
        }
    }
}
