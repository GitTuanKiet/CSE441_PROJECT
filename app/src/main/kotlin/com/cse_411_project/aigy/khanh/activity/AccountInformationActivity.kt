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
class AccountInformationActivity : AppCompatActivity() {
    private lateinit var ibtnBack: ImageButton
    private lateinit var edtFullName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var sharedPreferences: SharedPreferences

    private val userRepository = UserRepository()
    private val userViewModel = UserViewModel(userRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account_information)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        ibtnBack = findViewById(R.id.ibtn_back)
        ibtnBack.setOnClickListener { finish() }

        edtFullName = findViewById(R.id.et_full_name)
        edtEmail = findViewById(R.id.et_email)
        edtPhoneNumber = findViewById(R.id.et_phone_number)
        btnSaveChanges = findViewById(R.id.btn_save_changes)

        loadUserInfo()

        btnSaveChanges.setOnClickListener {
            updateUserInformation()
        }
    }

    private fun loadUserInfo() {
        edtFullName.setText(sharedPreferences.getString("fullName", ""))
        edtEmail.setText(sharedPreferences.getString("email", ""))
        edtPhoneNumber.setText(sharedPreferences.getString("phoneNumber", ""))
    }

    private fun updateUserInformation() {
        val newFullName = edtFullName.text.toString()
        val newEmail = edtEmail.text.toString()
        val newPhoneNumber = edtPhoneNumber.text.toString()

        // Create a UserModel object with updated information
        val updatedUser = UserModel(
            fullName = newFullName,
            email = newEmail,
            phoneNumber = newPhoneNumber,
            password = sharedPreferences.getString("password", "") ?: "",
            uid = sharedPreferences.getString("uid", "") ?: "",
            urlImage = sharedPreferences.getString("urlImage", "") ?: "",
            decentralization = sharedPreferences.getString("decentralization", "") ?: "",
            isOnline = sharedPreferences.getBoolean("isOnline", false),
            idListAgent = sharedPreferences.getStringSet("id_list_agent", setOf())?.toList() ?: emptyList(),
            conversationList = sharedPreferences.getStringSet("conversationList", setOf())?.toList() ?: emptyList(),
            referralCount = sharedPreferences.getInt("referralCount", 0)
        )

        // Update SharedPreferences
        with(sharedPreferences.edit()) {
            putString("fullName", newFullName)
            putString("email", newEmail)
            putString("phoneNumber", newPhoneNumber)
            apply()
        }

            userViewModel.updateUser(updatedUser) { isSuccess ->
                if (isSuccess) {
                    Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                    return@updateUser
                } else {
                    Toast.makeText(this, "Cập nhật thông tin thất bại!", Toast.LENGTH_SHORT).show()
                }
            }

    }
}
