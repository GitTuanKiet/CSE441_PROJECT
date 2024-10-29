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
import com.google.firebase.database.FirebaseDatabase

class AccountInformationActivity : AppCompatActivity() {
    private lateinit var ibtnBack: ImageButton
    private lateinit var edtFullName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPhoneNumber: EditText
    private lateinit var btnSaveChanges: Button
    private lateinit var sharedPreferences: SharedPreferences

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
        ibtnBack.setOnClickListener {
            finish()
        }

        edtFullName = findViewById(R.id.et_full_name)
        val fullName = sharedPreferences.getString("full_name", "")
        edtFullName.setText(fullName)

        edtEmail = findViewById(R.id.et_email)
        val email = sharedPreferences.getString("email", "")
        edtEmail.setText(email)

        edtPhoneNumber = findViewById(R.id.et_phone_number)
        val phoneNumber = sharedPreferences.getString("phone_number", "")
        edtPhoneNumber.setText(phoneNumber)

        btnSaveChanges = findViewById(R.id.btn_save_changes)
        btnSaveChanges.setOnClickListener {
            val newFullName = edtFullName.text.toString()
            val newEmail = edtEmail.text.toString()
            val newPhoneNumber = edtPhoneNumber.text.toString()

            val updates = mutableMapOf<String, Any>()

            if (newFullName.isNotEmpty() && newFullName != fullName) {
                updates["name"] = newFullName
                with(sharedPreferences.edit()) {
                    putString("full_name", newFullName)
                    apply()
                }
            }

            if (newEmail.isNotEmpty() && newEmail != email) {
                updates["email"] = newEmail
                with(sharedPreferences.edit()) {
                    putString("email", newEmail)
                    apply()
                }
            }

            if (newPhoneNumber.isNotEmpty() && newPhoneNumber != phoneNumber) {
                updates["phone"] = newPhoneNumber
                with(sharedPreferences.edit()) {
                    putString("phone_number", newPhoneNumber)
                    apply()
                }
            }

            val uid = sharedPreferences.getString("uid", "")
            val userRef = uid?.let { FirebaseDatabase.getInstance().getReference("users").child(it) }

            if (userRef != null && updates.isNotEmpty()) {
                userRef.updateChildren(updates)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Cập nhật thông tin thành công!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Lỗi cập nhật thông tin: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}