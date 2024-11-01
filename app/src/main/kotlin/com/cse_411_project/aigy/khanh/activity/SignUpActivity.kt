package com.cse_411_project.aigy.khanh.activity

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
import com.cse_411_project.aigy.khanh.model.UserModel
import com.cse_411_project.aigy.khanh.viewmodel.UserViewModel
import com.cse_411_project.aigy.khanh.fragment.PhoneNumberVerifyDialogFragment
import com.cse_411_project.aigy.khanh.repositories.UserRepository
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
//    private var isDialogOpen = false
    private lateinit var edtFullName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var phoneNumber: String

//    private lateinit var verificationCode: String
//    private lateinit var forceResendingToken: PhoneAuthProvider.ForceResendingToken

    private val userRepository = UserRepository()
    private val userViewModel = UserViewModel(userRepository)

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
        val phoneNumberInput = edtPhoneNumber.text.toString().trim()
        phoneNumber = if (phoneNumberInput.startsWith("0")) {
            "+84" + phoneNumberInput.substring(1)
        } else {
            "+84$phoneNumberInput"
        }

        if (validateInput(fullName, email, password, confirmPassword, phoneNumber)) {
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        userViewModel.createUser(
                            UserModel(
                                uid = firebaseAuth.currentUser!!.uid,
                                fullName = edtFullName.text.toString().trim(),
                                email = email,
                                password = password,
                                phoneNumber = phoneNumber,
                                urlImage = "",
                                decentralization = "user",
                                isOnline = true,
                                idListAgent = emptyList(),
                                conversationList = emptyList(),
                                referralCount = 0
                            )
                        ) { isSuccess ->
                            if (isSuccess) {
                                Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
                                openSignInActivity()
                            } else {
                                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        val ex = task.exception.toString()

                        if (ex.contains("The email address is already in use by another account",false)) {
                            Toast.makeText(this, "Email đã được sử dụng!", Toast.LENGTH_SHORT).show()
                        } else if (ex.contains("The email address is badly formatted", false)) {
                            Toast.makeText(this, "Email không hợp lệ!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show()
                            Log.e("SignUpActivity", ex)
                        }
                    }
                }
        }
    }
//
//    fun verifyOtp(otpCode: String) {
//        val credential = PhoneAuthProvider.getCredential(verificationCode, otpCode)
//        firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                // OTP đã được xác thực, tiến hành tạo tài khoản Firebase
//                val email = edtEmail.text.toString().trim()
//                val password = edtPassword.text.toString().trim()
//
//                firebaseAuth.createUserWithEmailAndPassword(email, password)
//                    .addOnCompleteListener { createTask ->
//                        if (createTask.isSuccessful) {
//                            userViewModel.createUser(
//                                UserModel(
//                                    uid = firebaseAuth.currentUser!!.uid,
//                                    fullName = edtFullName.text.toString().trim(),
//                                    email = email,
//                                    password = password,
//                                    phoneNumber = phoneNumber,
//                                    urlImage = "",
//                                    decentralization = "user",
//                                    isOnline = true,
//                                    idListAgent = emptyList(),
//                                    conversationList = emptyList(),
//                                    referralCount = 0
//                                )
//                            )
//                            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show()
//                            openSignInActivity()
//                        } else {
//                            val ex = task.exception.toString()
//
//                            if (ex.contains("The email address is already in use by another account", false)) {
//                                Toast.makeText(this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show()
//                                finish()
//                            } else if (ex.contains("The email address is badly formatted", false)) {
//                                Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
//                                finish()
//                            } else {
//                                Toast.makeText(this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
//                                Log.e("SignUpActivity", ex)
//                            }
//                        }
//                    }
//            } else {
//                Toast.makeText(this, "Xác thực OTP thất bại", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//
//    private fun sendVerificationCode(number: String) {
//        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
//            .setPhoneNumber(number)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
//            .setCallbacks(callbacks)
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }
//
//    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
//            firebaseAuth.signInWithCredential(credential).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    if (isDialogOpen) {
//                        val phoneNumberVerifyDialog =
//                            supportFragmentManager.findFragmentByTag("PhoneNumberVerifyDialog") as PhoneNumberVerifyDialogFragment
//                        phoneNumberVerifyDialog.dismiss()
//                    }
//                    Toast.makeText(this@SignUpActivity, "Đăng ký thành công", Toast.LENGTH_SHORT)
//                        .show()
//                    userViewModel.createUser(
//                        UserModel(
//                            uid = firebaseAuth.currentUser!!.uid,
//                            fullName = edtFullName.text.toString().trim(),
//                            email = edtEmail.text.toString().trim(),
//                            password = edtPassword.text.toString().trim(),
//                            phoneNumber = phoneNumber,
//                            urlImage = "",
//                            decentralization = "user",
//                            isOnline = true,
//                            idListAgent = emptyList(),
//                            conversationList = emptyList(),
//                            referralCount = 0
//                        )
//                    )
//                    openSignInActivity()
//                } else {
//                    val ex = task.exception.toString()
//
//                    if (ex.contains("The email address is already in use by another account", false)) {
//                        Toast.makeText(this@SignUpActivity, "Email đã được sử dụng", Toast.LENGTH_SHORT).show()
//                        finish()
//                    } else if (ex.contains("The email address is badly formatted", false)) {
//                        Toast.makeText(this@SignUpActivity, "Email không hợp lệ", Toast.LENGTH_SHORT).show()
//                        finish()
//                    } else {
//                        Toast.makeText(this@SignUpActivity, "Đăng ký thất bại", Toast.LENGTH_SHORT).show()
//                        Log.e("SignUpActivity", ex)
//                    }
//                }
//            }
//        }
//
//        override fun onVerificationFailed(e: FirebaseException) {
//            Toast.makeText(
//                this@SignUpActivity,
//                "Xác thực OTP thất bại: ${e.message}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//
//        override fun onCodeSent(
//            verificationId: String,
//            token: PhoneAuthProvider.ForceResendingToken
//        ) {
//            verificationCode = verificationId
//            forceResendingToken = token
//        }
//    }
//
//    private fun openPhoneNumberVerifyFragment() {
//        isDialogOpen = true
//        val phoneNumberVerifyDialog = PhoneNumberVerifyDialogFragment()
//
//        val bundle = Bundle()
//        bundle.putString("phone_number", phoneNumber)
//        phoneNumberVerifyDialog.arguments = bundle
//
//        phoneNumberVerifyDialog.isCancelable = false
//        phoneNumberVerifyDialog.setOnDismissListener(object :
//            PhoneNumberVerifyDialogFragment.OnDialogDismissListener {
//            override fun onDialogDismiss() {
//                isDialogOpen = false
//            }
//        })
//        phoneNumberVerifyDialog.show(supportFragmentManager, "PhoneNumberVerifyDialog")
//    }
//
//    fun resendOtp() {
//        val phoneNumber = phoneNumber
//        sendVerificationCode(phoneNumber)
//        Toast.makeText(this, "Mã xác thực đã được gửi lại!", Toast.LENGTH_SHORT).show()
//    }

    private fun openSignInActivity() {
        val intent = SignInActivity.newIntent(this)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    private fun validateInput(
        fullName: String,
        email: String,
        password: String,
        confirmPassword: String,
        phoneNumber: String
    ): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+" // Định dạng email cơ bản
        val phonePattern = "^(\\+84|0)[1-9][0-9]{8,9}$" // Định dạng số điện thoại hợp lệ cho Việt Nam

        return when {
            fullName.isEmpty() -> {
                edtFullName.error = "Vui lòng nhập họ và tên"
                false
            }

            email.isEmpty() -> {
                edtEmail.error = "Vui lòng nhập email"
                false
            }

            !email.matches(emailPattern.toRegex()) -> {
                edtEmail.error = "Email không đúng định dạng"
                false
            }

            password.isEmpty() -> {
                edtPassword.error = "Vui lòng nhập mật khẩu"
                false
            }

            password.length < 6 -> {
                edtPassword.error = "Mật khẩu phải có ít nhất 6 ký tự"
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

            phoneNumber.isEmpty() -> {
                edtPhoneNumber.error = "Vui lòng nhập số điện thoại"
                false
            }

            !phoneNumber.matches(phonePattern.toRegex()) -> {
                edtPhoneNumber.error = "Số điện thoại không hợp lệ"
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
