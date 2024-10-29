package com.tuankiet.sample.features.admin.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseActivity
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.databinding.ActivityLayoutBinding
import com.tuankiet.sample.databinding.FragmentAdminBinding
import com.tuankiet.sample.databinding.FragmentAgentsBinding
import com.tuankiet.sample.features.admin.data.UserModel

class AdminFragment : BaseFragment() {
    private lateinit var binding: ActivityLayoutBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = ActivityLayoutBinding().inflate(layoutInflater)
//        binding.toolBarContainer.
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin, container, false)


        return view
    }

    fun updateUserList(users: List<UserModel>) {
    }
}