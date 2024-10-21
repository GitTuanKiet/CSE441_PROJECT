package com.tuankiet.sample.features.admin.ui.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseActivity
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.ActivityLayoutBinding
import com.tuankiet.sample.features.admin.data.UserRepository
import com.tuankiet.sample.features.admin.ui.fragments.AdminFragment


class Admin_Activity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, Admin_Activity::class.java)
    }

    private lateinit var binding: ActivityLayoutBinding




    override fun fragment(): BaseFragment {
        return AdminFragment() // Khởi tạo fragment cho Admin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
        // Quan sát danh sách người dùng
//        userViewModel.users.observe(this, { users ->
//            // Cập nhật giao diện người dùng với danh sách người dùng
//            // Ví dụ: Cập nhật RecyclerView trong AdminFragment
//            (supportFragmentManager.findFragmentById(fragmentContainer().id) as AdminFragment).updateUserList(users)
//        })

//        // Lấy danh sách người dùng ngay khi Activity được tạo
//        userViewModel.fetchUsers()
    }

}





