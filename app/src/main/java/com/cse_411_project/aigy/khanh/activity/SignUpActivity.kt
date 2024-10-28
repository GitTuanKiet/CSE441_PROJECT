package com.cse_411_project.aigy.khanh.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.khanh.fragment.PhoneNumberVerifyDialogFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class SignUpActivity : AppCompatActivity() {
    private lateinit var iBtnBack: ImageButton
    private lateinit var btnRegister: Button
    private lateinit var btnSignIn: TextView
    private var isDialogOpen = false
    private lateinit var edtFullName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var verificationCode: String
    private lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        btnRegister = findViewById(R.id.btn_register)
        edtFullName = findViewById(R.id.tb_full_name)
        edtEmail = findViewById(R.id.tb_email)
        edtPassword = findViewById(R.id.tb_password)
        edtConfirmPassword = findViewById(R.id.tb_confirm_password)
        edtPhoneNumber = findViewById(R.id.tb_phone_number)

        btnRegister.setOnClickListener {
            registerUser()
        }

        iBtnBack = findViewById(R.id.ibtn_back)
        iBtnBack.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

        btnSignIn = findViewById(R.id.txt_sign_in)
        btnSignIn.setOnClickListener {
            openSignInActivity()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun registerUser() {
        val fullName = edtFullName.text.toString().trim()
        val email = edtEmail.text.toString().trim()
        val password = edtPassword.text.toString().trim()
        val confirmPassword = edtConfirmPassword.text.toString().trim()
        val phoneNumber = "+84" + edtPhoneNumber.text.toString().trim()

        if (validateInput(fullName, email, password, confirmPassword, phoneNumber)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    sendVerificationCode(phoneNumber)
                    openPhoneNumberVerifyFragment()
                    Toast.makeText(this, "OTP đã được gửi đến số điện thoại của bạn", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    if (isDialogOpen) {
                        val phoneNumberVerifyDialog = supportFragmentManager.findFragmentByTag("PhoneNumberVerifyDialog") as PhoneNumberVerifyDialogFragment
                        phoneNumberVerifyDialog.dismiss()
                    }
                    Toast.makeText(this@SignUpActivity, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                    openSignInActivity()
                } else {
                    Toast.makeText(this@SignUpActivity, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@SignUpActivity, "Xác thực OTP thất bại: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            verificationCode = verificationId
            forceResendingToken = token
        }
    }

    private fun openPhoneNumberVerifyFragment() {
        isDialogOpen = true
        val phoneNumberVerifyDialog = PhoneNumberVerifyDialogFragment()
        phoneNumberVerifyDialog.isCancelable = false
        phoneNumberVerifyDialog.setOnDismissListener(object : PhoneNumberVerifyDialogFragment.OnDialogDismissListener {
            override fun onDialogDismiss() {
                isDialogOpen = false
            }
        })
        phoneNumberVerifyDialog.show(supportFragmentManager, "PhoneNumberVerifyDialog")
    }

    fun verifyOtp(otpCode: String) {
        val credential = PhoneAuthProvider.getCredential(verificationCode, otpCode)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Toast.makeText(this, "Xác thực OTP thành công", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Chuyển sang màn hình đăng nhập", Toast.LENGTH_SHORT).show()
                openSignInActivity()
            } else {
                Toast.makeText(this, "Xác thực OTP thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun resendOtp() {
        val phoneNumber = "+84" + edtPhoneNumber.text.toString().trim()
        sendVerificationCode(phoneNumber)
        Toast.makeText(this, "Mã xác thực đã được gửi lại!", Toast.LENGTH_SHORT).show()
    }

    private fun openSignInActivity() {
        val intent = SignInActivity.newIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun validateInput(fullName: String, email: String, password: String, confirmPassword: String, phoneNumber: String): Boolean {
        return when {
            fullName.isEmpty() -> {
                edtFullName.error = "Vui lòng nhập họ và tên"
                false
            }
            email.isEmpty() -> {
                edtEmail.error = "Vui lòng nhập email"
                false
            }
            password.isEmpty() -> {
                edtPassword.error = "Vui lòng nhập mật khẩu"
                false
            }
            confirmPassword.isEmpty() -> {
                edtConfirmPassword.error = "Vui lòng nhập lại mật khẩu"
                false
            }
            password != confirmPassword -> {
                edtConfirmPassword.error = "Mật khẩu không khớp"
                false
            }
            phoneNumber.length < 10 -> {
                edtPhoneNumber.error = "Vui lòng nhập số điện thoại hợp lệ"
                false
            }
            else -> true
        }
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, SignUpActivity::class.java)
        }
    }
}
