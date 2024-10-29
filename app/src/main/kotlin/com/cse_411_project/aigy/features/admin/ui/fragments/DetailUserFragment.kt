package com.cse_411_project.aigy.features.admin.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.cse_411_project.aigy.R
import com.cse_411_project.aigy.core.platform.BaseFragment
import com.cse_411_project.aigy.features.admin.data.repositories.UserRepository
import com.cse_411_project.aigy.features.admin.ui.viewmodel.UserViewModel
import com.cse_411_project.aigy.features.admin.ui.viewmodel.UserViewModelFactory

class DetailUserFragment : BaseFragment() {
    private val userRepository = UserRepository()
    private val viewModel: UserViewModel by viewModels { UserViewModelFactory(userRepository) }
    private lateinit var view: View
    private lateinit var imgUrl : ImageView
    private lateinit var txtName : TextView
    private lateinit var txtEmail : TextView
    private lateinit var txtStatus : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view = inflater.inflate(R.layout.fragment_detail_user, container, false)
        Map()
        getData()
        return view
    }

    private fun getData() {
        val uid = arguments?.getString("uid") ?: return
        try {
            viewModel.getUserByUID(uid)
            viewModel.selectedUser.observe(viewLifecycleOwner) { user ->
                user?.let {
                    txtName.text = it.name
                    if (it.email.isNotEmpty()) {
                        txtEmail.text = it.email
                    } else {
                        txtEmail.text = it.phone
                    }
                    txtStatus.text = if (it.isOnline) "Online" else "Offline"
                    Glide.with(this)
                        .load(it.urlImg)
                        .centerCrop()
                        .into(imgUrl)
                } ?: run {
                    Log.d("loi", "User not found")
                }
            }
        }catch (e : Exception){
            Log.d("loi" , e.message.toString())
        }

    }

    fun Map(){
        imgUrl = view.findViewById(R.id.imgUrl)
        txtName = view.findViewById(R.id.txtName)
        txtEmail = view.findViewById(R.id.txtEmail_Phone)
        txtStatus = view.findViewById(R.id.txtStatus)
    }
}