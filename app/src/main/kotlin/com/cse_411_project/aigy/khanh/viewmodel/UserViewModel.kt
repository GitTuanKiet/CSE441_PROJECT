package com.cse_411_project.aigy.khanh.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cse_411_project.aigy.core.failure.Failure
import com.cse_411_project.aigy.core.platform.BaseViewModel
import com.cse_411_project.aigy.features.admin.data.models.UserModel

class UserViewModel(private val userRepository: com.cse_411_project.aigy.khanh.repositories.UserRepository) : BaseViewModel() {
    private val _users: MutableLiveData<List<UserModel>> = MutableLiveData()
    val users: LiveData<List<UserModel>> = _users
    private val _selectedUser: MutableLiveData<UserModel?> = MutableLiveData()

    fun getUserByEmail(name: String) {
        userRepository.getUserByName(name,
            onComplete = { user ->
                _users.value = user
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun getUserByUID(uid: String) {
        userRepository.getUserByUID(uid,
            onComplete = { user ->
                _selectedUser.value = user
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun updateUser(user: com.cse_411_project.aigy.khanh.model.UserModel) {
        userRepository.updateUser(user,
            onComplete = {

            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun createUser(user: com.cse_411_project.aigy.khanh.model.UserModel) {
        userRepository.createUser(user,
            onComplete = {
                Log.d("loi" , "Tạo user thành công")
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }
}