package com.tuankiet.sample.features.admin.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tuankiet.sample.R
import com.tuankiet.sample.core.platform.BaseFragment
import com.tuankiet.sample.features.admin.data.repositorys.UserRepository
import com.tuankiet.sample.features.admin.ui.adapters.UserAdapter
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModel
import com.tuankiet.sample.features.admin.ui.viewmodel.UserViewModelFactory

class UseManagementFragment : BaseFragment() {
    private lateinit var listUser: RecyclerView
    private lateinit var userAdapter: UserAdapter
    private lateinit var txtSearch : EditText
    private lateinit var view: View
    private val handler = Handler(Looper.getMainLooper())
    private var runnable: Runnable? = null
    private val userRepository = UserRepository()
    private val viewModel: UserViewModel by viewModels { UserViewModelFactory(userRepository) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_use_management, container, false)
        Map()
        getData()
        getEvent()

        return view
    }
    fun Map(){
        listUser = view.findViewById(R.id.listUser)
        listUser.layoutManager = LinearLayoutManager(requireContext())
        txtSearch = view.findViewById(R.id.txtSearch)
    }
    fun getData(){
        // Khởi tạo adapter với danh sách rỗng
        userAdapter = UserAdapter(emptyList() , viewModel , parentFragmentManager)
        listUser.adapter = userAdapter

        // Lấy dữ liệu người dùng
        viewModel.fetchUsers()

        // Quan sát thay đổi dữ liệu người dùng
        viewModel.users.observe(viewLifecycleOwner, { userList ->
            userList?.let {
                userAdapter.updateUserList(it)  // Cập nhật danh sách người dùng cho adapter
            } ?: Log.e("loi", "User list is null")
        })

    }
    fun getEvent(){
        txtSearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {

                runnable?.let { handler.removeCallbacks(it) }

                runnable = Runnable {
                    viewModel.getUserByName(s.toString())
                }
                handler.postDelayed(runnable!!, 500) // Delay 500ms
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
}

