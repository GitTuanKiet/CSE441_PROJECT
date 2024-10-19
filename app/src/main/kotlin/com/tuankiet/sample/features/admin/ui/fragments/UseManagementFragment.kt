package com.tuankiet.sample.features.admin.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.features.admin.data.UserModel
import com.tuankiet.sample.features.admin.ui.adapters.UserAdapter


class UseManagementFragment : BaseFragment() {
    private var listUser: RecyclerView? = null
    private val userLists: ArrayList<UserModel> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_use_management, container, false)

        listUser = view.findViewById(R.id.listUser)

        userLists.add(UserModel("1223", "Phạm Xuân Trường", "Pham Xuân Trường@gmail.com" , "11213132" , "https://www.google.com/", "Admin", true, emptyList () ))
        userLists.add(UserModel("1223", "Phạm Xuân Trường", "Pham Xuân Trường@gmail.com" , "11213132" , "https://www.google.com/", "Admin", true, emptyList () ))
        userLists.add(UserModel("1223", "Phạm Xuân Trường", "Pham Xuân Trường@gmail.com" , "11213132" , "https://www.google.com/", "Admin", true, emptyList () ))
        val adapter: UserAdapter = UserAdapter(view.context, userLists)
        listUser?.setAdapter(adapter)
        listUser?.setLayoutManager(LinearLayoutManager(view.context))
        return view
    }
}