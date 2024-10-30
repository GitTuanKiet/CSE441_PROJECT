package com.cse_411_project.aigy.features.admin.ui.fragments

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
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.platform.BaseFragment
import com.cse_411_project.aigy.databinding.ActivityLayoutBinding
import com.cse_411_project.aigy.features.admin.data.repositories.DateRespository
import com.cse_411_project.aigy.features.admin.data.repositories.UserRepository
import com.cse_411_project.aigy.features.admin.ui.viewmodel.DateViewModel
import com.cse_411_project.aigy.features.admin.ui.viewmodel.UserViewModel
import de.hdodenhof.circleimageview.CircleImageView
import java.util.Locale
import androidx.activity.OnBackPressedCallback
import com.cse_411_project.aigy.features.admin.data.models.UserModel

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
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val currentFragment = parentFragmentManager.findFragmentById(R.id.displayData)

                if (currentFragment is HomeAdminFragment) {
                    showBackConfirmationDialog()
                } else {
                    parentFragmentManager.popBackStack()
                }
            }
        })
        if (savedInstanceState == null) {
//            userViewModel.createUser(UserModel("3I6rwDv6ZecpGDF2kI6LkrWJ0R43","Trường" , "truongpham@gmail.com" , "" , "link_anh" , "user" , false , emptyList() , emptyList()))
//            userViewModel.createUser(UserModel("EOHtOEWvqXZYRV3l8F9r7bKPrfg1","Trường" , "phamxuantruong@gmail.com" , "" , "link_anh" , "admin" , false , emptyList()))
//            userViewModel.createUser(UserModel("567","Khanh" , "nguyenducanh@gmail.com" , "" , "link_anh" , "user" , false , emptyList()))
//            userViewModel.createUser(UserModel("789","Anh" , "nguyenducanh@gmail.com" , "" , "link_anh" , "user" , false , emptyList()))
//            userViewModel.createAgent("123" , "12343214")
//            userViewModel.createConversation("3","234" , "12343214")
//            userViewModel.createMessage("1" ,"3" , "123" , "Xin chào nha" , "text" , 0 , System.currentTimeMillis())\
        }
    }

    private fun showBackConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận")
            .setMessage("Bạn muốn thoát khỏỉ chương trình chứ?")
            .setPositiveButton("Có") { dialog, _ ->
                dialog.dismiss()
                requireActivity().finishAffinity()
            }
            .setNegativeButton("Không") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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

            }
        }
        getAdmin("3I6rwDv6ZecpGDF2kI6LkrWJ0R43")
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
                    val currentUser = auth.currentUser
                    currentUser?.let {
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


    private fun replaceFragment(newFragment: Fragment) {
            val fragmentManager = parentFragmentManager
            fragmentManager.popBackStack()
            fragmentManager.beginTransaction()
                .replace(R.id.displayData, newFragment)
                .addToBackStack(null)
                .commit()
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
                    5 -> child.setOnClickListener{ confirmLogout()}
                }
            }
        }
        avatarImg.setOnClickListener{
            openImagePicker()
        }
    }
    private fun confirmLogout(){
        Log.d("loi" , "Logout")
        AlertDialog.Builder(requireContext())
            .setTitle("Xác nhận")
            .setMessage("Bạn có chắc chắn muốn đăng xuất không?")
            .setPositiveButton("Có") { dialog, _ ->
                Logout()
                dialog.dismiss()
                /// Quay lại trang đầu nha các cậu :)))
            }
            .setNegativeButton("Không") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }
    private fun Logout(){
        val auth = FirebaseAuth.getInstance()
        auth.signOut()
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
                replaceFragment(myFragment1)
            }
            1 -> {
                val myFragment2 = UseManagementFragment()
                replaceFragment((myFragment2))
            }
        }

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
