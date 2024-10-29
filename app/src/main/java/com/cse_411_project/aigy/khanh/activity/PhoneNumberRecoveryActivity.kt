package com.cse_411_project.aigy.khanh.activity

import android.content.Context
import android.content.Intent
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
import com.cse_411_project.aigy.khanh.fragment.PhoneNumberVerifyDialogFragment
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class PhoneNumberRecoveryActivity : AppCompatActivity() {
    private lateinit var iBtnBack: ImageButton
    private lateinit var btnNext: Button
    private lateinit var edtPhoneNumber: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var verificationCode: String
    private lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken
    private var isDialogOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_phone_number_recovery)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()

        iBtnBack = findViewById(R.id.ibtn_back)
        iBtnBack.setOnClickListener { finish() }

        edtPhoneNumber = findViewById(R.id.et_phone_number)

        btnNext = findViewById(R.id.btn_next)
        btnNext.setOnClickListener {
            sendVerificationCode()
        }
    }

    private fun sendVerificationCode() {
        val phoneNumber = "+84" + edtPhoneNumber.text.toString().trim()

        if (phoneNumber.isEmpty()) {
            sendVerificationCode(phoneNumber)
            openPhoneNumberVerifyFragment()
            Toast.makeText(this, "OTP đã được gửi đến số điện thoại của bạn", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
        }
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            val otpCode = credential.smsCode
            if (!otpCode.isNullOrBlank()) {
                verifyOtp(otpCode)
            }
        }
        override fun onVerificationFailed(e: FirebaseException) {
            Toast.makeText(this@PhoneNumberRecoveryActivity, "Xác thực OTP thất bại: ${e.message}", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
            verificationCode = verificationId
            forceResendingToken = token
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

    private fun openPhoneNumberVerifyFragment() {
        isDialogOpen = true
        val phoneNumberVerifyDialog = PhoneNumberVerifyDialogFragment()

        val bundle = Bundle()
        bundle.putString("phone_number", "+84" + edtPhoneNumber.text.toString().trim())
        phoneNumberVerifyDialog.arguments = bundle

        phoneNumberVerifyDialog.isCancelable = false
        phoneNumberVerifyDialog.setOnDismissListener(object : PhoneNumberVerifyDialogFragment.OnDialogDismissListener {
            override fun onDialogDismiss() {
                isDialogOpen = false
            }
        })
        phoneNumberVerifyDialog.show(supportFragmentManager, "PhoneNumberVerifyDialog")
    }

    fun verifyOtp(otpCode: String?) {
        val credential = PhoneAuthProvider.getCredential(verificationCode, otpCode ?: "")
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val intent = ResetPasswordActivity.newIntent(this, edtPhoneNumber.text.toString().trim())
                startActivity(intent)
                finish()
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

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, PhoneNumberRecoveryActivity::class.java)
        }
    }
}
