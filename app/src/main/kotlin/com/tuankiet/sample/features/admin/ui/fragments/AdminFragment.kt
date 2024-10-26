package com.tuankiet.sample.features.admin.ui.fragments

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.values
import com.google.firebase.storage.FirebaseStorage
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.ActivityLayoutBinding
import com.tuankiet.sample.features.admin.data.models.UserModel
import com.tuankiet.sample.features.admin.data.repositorys.DateRespository
import com.tuankiet.sample.features.admin.data.repositorys.UserRepository
import com.tuankiet.sample.features.admin.ui.viewmodel.DateViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Suppress("DEPRECATION")
class AdminFragment : BaseFragment() {
    private lateinit var binding: ActivityLayoutBinding
    private lateinit var btnMenu: ImageButton
    private lateinit var taskBar: DrawerLayout
    private lateinit var navbar: TableLayout
    private lateinit var locale: Locale
    private lateinit var avatarImg :CircleImageView
    private lateinit var txtName : TextView
    private lateinit var prefs: SharedPreferences
    private lateinit var newLanguage: String
    private lateinit var currentLanguage: String
    val PICK_IMAGE_REQUEST = 1
    private val userRepository = UserRepository()
    private val dateRepository = DateRespository()
    private val dateViewModel = DateViewModel(dateRepository)
    private val userViewModel = UserViewModel(userRepository)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        prefs = requireContext().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        currentLanguage = prefs.getString("language", "en").toString()
        setLocale(currentLanguage)
        val isDarkMode = prefs.getBoolean("isDarkMode", false)
        setDarkMode(isDarkMode)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {
//            userViewModel.createUser(UserModel("wT33iP7XoMOr6tZ8CFGKHEQWTMy2","Kiet" , "nguyenducanh@gmail.com" , "" , "link_anh" , "user" , false , emptyList()))
//            userViewModel.createUser(UserModel("EOHtOEWvqXZYRV3l8F9r7bKPrfg1","Trường" , "phamxuantruong@gmail.com" , "" , "link_anh" , "admin" , false , emptyList()))
//            userViewModel.createUser(UserModel("567","Khanh" , "nguyenducanh@gmail.com" , "" , "link_anh" , "user" , false , emptyList()))
//            userViewModel.createUser(UserModel("789","Anh" , "nguyenducanh@gmail.com" , "" , "link_anh" , "user" , false , emptyList()))
//            userViewModel.createAgent("123" , "12343214")
//            userViewModel.createConversation("3","234" , "12343214")
//            userViewModel.createMessage("1" ,"3" , "123" , "Xin chào nha" , "text" , 0 , System.currentTimeMillis())\
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_admin, container, false)
        Mapping(view)
        if (savedInstanceState == null) {
            val currentFragment = parentFragmentManager.findFragmentById(R.id.displayData)
            if (currentFragment == null) {
                val pageIndex = prefs.getInt("pageIndex", 0)
                loadingFragment(pageIndex)
//                addVisit()
//                userViewModel.createMessage("3" ,"3" , "123" , "Ừ chào cậu" , "text" , 0 , System.currentTimeMillis())
//                registerUser("truongphamdz2le3@gmail.com", "123456",
//                    onComplete = { Log.d("loi", "Đăng ký thành công.") },
//                    onError = { error -> Log.d("loi", "Lỗi: ${error.message}") }
//                )

// Đăng nhập
//                loginUser("phamxuantruong@gmail.com", "123456",
//                    onComplete = {
//                        Log.d("loi", "Đăng nhập thành công.")
//
//                     },
//                    onError = { error -> Log.d("loi", "Lỗi: ${error.message}") }
//                )
                getAdmin("EOHtOEWvqXZYRV3l8F9r7bKPrfg1")
            }
        }
        getEvent()

        return view
    }
    fun getAdmin(uid : String ){
        userViewModel.getUserByUID(uid)
        userViewModel.selectedUser.observe(viewLifecycleOwner){
            user ->
            user?.let{
                val name = it.name
                val avatar = it.urlImg
                txtName.text = name
                Glide
                    .with(this)
                    .load(avatar)
                    .centerCrop()
                    .into(avatarImg)
            }
        }
    }
    fun registerUser(email: String, password: String, onComplete: () -> Unit, onError: (Exception) -> Unit) {
        val auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Đăng ký thành công, lấy thông tin currentUser
                    val currentUser = auth.currentUser
                    currentUser?.let {
                        // Làm gì đó với currentUser nếu cần
                        onComplete()
                    }
                } else {
                    // Xử lý lỗi
                    onError(task.exception ?: Exception("Đăng ký không thành công."))
                }
            }
    }
    fun loginUser(email: String, password: String, onComplete: () -> Unit, onError: (Exception) -> Unit) {
         val auth = FirebaseAuth.getInstance()

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    currentUser?.let {
                        onComplete()
                    }
                } else {
                    onError(task.exception ?: Exception("Đăng nhập không thành công."))
                }
            }
    }

    private fun addVisit() {
        dateViewModel.updateDate()
    }


    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data
            uploadImageToFirebase(imageUri)
        }
    }


    fun uploadImageToFirebase(imageUri: Uri?) {
        if (imageUri == null) return


        val userId = FirebaseAuth.getInstance().currentUser?.uid
        if (userId == null) {
            return
        }

        val storageReference = FirebaseStorage.getInstance().getReference("avatar/$userId.jpg")

        storageReference.putFile(imageUri)
            .addOnSuccessListener { taskSnapshot ->
                storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    saveImageUrlToDatabase(downloadUri.toString())
                }
                    .addOnFailureListener { e ->
                        Log.e("loi", "Failed to get download URL: ${e.message}")
                    }
            }
            .addOnFailureListener { e ->
                Log.e("loi", "Failed to upload image: ${e.message}")
            }
    }
    fun saveImageUrlToDatabase(imageUrl: String) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return
        val databaseReference = FirebaseDatabase.getInstance().getReference("users/$userId/urlImg")

        databaseReference.setValue(imageUrl)
            .addOnSuccessListener {
                Log.d("loi", "Image URL saved successfully.")
                Glide.with(requireContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(avatarImg)
            }
            .addOnFailureListener { e ->
                Log.e("loi", "Failed to save image URL: ${e.message}")
            }
    }
    private fun getEvent() {
        btnMenu.setOnClickListener {
            if (taskBar.isDrawerOpen(GravityCompat.START)) {
                taskBar.closeDrawer(GravityCompat.START)
            } else {
                taskBar.openDrawer(GravityCompat.START)
            }
        }

        for (i in 0 until navbar.childCount) {
            val child = navbar.getChildAt(i)
            if (child is TableRow) {
                when (i) {
                    0 -> child.setOnClickListener { loadingFragment(0) }
                    1 -> child.setOnClickListener { loadingFragment(1) }
                    2 -> child.setOnClickListener { toggleDarkMode() }
                    3 -> child.setOnClickListener { switchLanguage() }
                    4 -> Log.d("AdminFragment", "Logout clicked")
                }
            }
        }
        avatarImg.setOnClickListener{
            openImagePicker()
        }
    }

    private fun Mapping(view: View?) {
        taskBar = view?.findViewById(R.id.Taskbar)!!
        navbar = view.findViewById(R.id.NavBar)
        btnMenu = view.findViewById(R.id.btnMenu)
        avatarImg = view.findViewById(R.id.avatar)
        txtName = view.findViewById(R.id.txtName)
    }

    private fun loadingFragment(pageIndex: Int) {
        val fragmentManager: FragmentManager = parentFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        when (pageIndex) {
            0 -> {
                val myFragment1 = HomeAdminFragment()
                fragmentTransaction.replace(R.id.displayData, myFragment1)
            }
            1 -> {
                val myFragment2 = UseManagementFragment()
                fragmentTransaction.replace(R.id.displayData, myFragment2)
            }
        }
        fragmentTransaction.commit()

    }

    private fun toggleDarkMode() {
        val isDarkMode = prefs.getBoolean("isDarkMode", false)
        val pageIndex = prefs.getInt("pageIndex", 0)
        prefs.edit().putInt("pageIndex", pageIndex).apply()
        if (isDarkMode) {
            prefs.edit().putBoolean("isDarkMode", false).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        } else {
            prefs.edit().putBoolean("isDarkMode", true).apply()
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        requireActivity().recreate()
    }

    private fun switchLanguage() {
        newLanguage = if (currentLanguage == "en") "vi" else "en"
        val pageIndex = prefs.getInt("pageIndex", 0)
        prefs.edit().putInt("pageIndex", pageIndex).apply()
        setLocale(newLanguage)
        prefs.edit().putString("language", newLanguage).apply()
        requireActivity().recreate()
    }

    private fun setLocale(languageCode: String) {
        locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setDarkMode(isDarkMode: Boolean) {
        if (isDarkMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

}
