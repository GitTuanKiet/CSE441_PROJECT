package com.tuankiet.sample.features.admin.ui.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.tuankiet.sample.core.platform.BaseActivity
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.ActivityLayoutBinding

import com.tuankiet.sample.features.admin.ui.fragments.AdminFragment


class AdminActivity : BaseActivity() {
    companion object {
        fun callingIntent(context: Context) = Intent(context, AdminActivity::class.java)
    }

    private lateinit var binding: ActivityLayoutBinding


    override fun fragment(): BaseFragment {
        return AdminFragment() // Khởi tạo fragment cho Admin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLayoutBinding.inflate(layoutInflater)
    }
}