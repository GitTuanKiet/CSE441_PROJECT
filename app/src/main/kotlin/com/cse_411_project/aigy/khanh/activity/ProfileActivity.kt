package com.cse_411_project.aigy.khanh.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.features.chat.ExploreActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {
    private lateinit var ibtnBack: ImageButton
    private lateinit var lbtnPreferences: LinearLayout
    private lateinit var lbtnHelp: LinearLayout
    private lateinit var lbtnLogout: LinearLayout
    private lateinit var txtEmail: TextView
    private lateinit var txtFullName: TextView
    private lateinit var ibtnAvatar: ImageButton
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var imageUri: Uri
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        firebaseAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()

        ibtnAvatar = findViewById(R.id.ibtn_avatar)
        ibtnAvatar.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    0
                )
            } else {
//                startImagePicker()
            }
        }

        ibtnBack = findViewById(R.id.ibtn_back)
        ibtnBack.setOnClickListener {
            finish()
        }

        lbtnPreferences = findViewById(R.id.lbtn_preferences)
        lbtnPreferences.setOnClickListener {
            val intent = Intent(this, PreferencesActivity::class.java)
            startActivity(intent)
        }

        lbtnHelp = findViewById(R.id.lbtn_support)
        lbtnHelp.setOnClickListener {
//            val intent = Intent(this, HelpActivity::class.java)
//            startActivity(intent)
        }

        lbtnLogout = findViewById(R.id.lbtn_logout)
        lbtnLogout.setOnClickListener {
            firebaseAuth.signOut()
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        Log.d("prefs", sharedPreferences.all.toString())

        txtEmail = findViewById(R.id.hidden_email)
        txtEmail.text = sharedPreferences.getString("email", "")

//        txtFullName = findViewById(R.id.txt_full_name_user)
//        txtFullName.text = sharedPreferences.getString("fullName", "")
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, ProfileActivity::class.java)
        }

        fun callingIntent(context: Context) = Intent(context, ProfileActivity::class.java)
    }

//    private val cropImage = registerForActivityResult(CropImageContract()) { result ->
//        if (result.isSuccessful) {
//            val croppedImageUri = result.uriContent
//            croppedImageUri?.let { uri ->
//                uploadImageToFirebase(uri)
//            }
//        } else {
//            val exception = result.error
//            exception?.printStackTrace()
//        }
//    }
//
//    private fun startImagePicker() {
//        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
//        startActivityForResult(intent, REQUEST_IMAGE_PICKER)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK) {
//            data?.data?.let { uri ->
//                cropImage.launch(uri)
//            }
//        }
//    }
//
//    private fun uploadImageToFirebase(uri: Uri) {
//        val user = firebaseAuth.currentUser
//        user?.let {
//            val ref = storage.reference.child("avatars/${user.uid}.jpg")
//            ref.putFile(uri)
//                .addOnSuccessListener {
//                    ref.downloadUrl.addOnSuccessListener { downloadUri ->
//                        saveImageUrlToDatabase(downloadUri.toString())
//                    }
//                }
//                .addOnFailureListener { e ->
//                    e.printStackTrace()
//                }
//        }
//    }
//
//    private fun saveImageUrlToDatabase(imageUrl: String) {
//        val user = firebaseAuth.currentUser
//        user?.let {
//            val database = FirebaseDatabase.getInstance().reference.child("users").child(user.uid)
//            database.child("image_url").setValue(imageUrl)
//                .addOnSuccessListener {
//                    Toast.makeText(this, "Profile image updated", Toast.LENGTH_SHORT).show()
//                }
//                .addOnFailureListener { e ->
//                    e.printStackTrace()
//                }
//        }
//    }
//
//    companion object {
//        private const val REQUEST_IMAGE_PICKER = 1001
//    }
}
