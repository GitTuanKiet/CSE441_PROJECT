package com.cse_411_project.aigy.khanh.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cse_411_project.aigy.khanh.core.failure.Failure
import com.cse_411_project.aigy.khanh.core.platform.BaseViewModel
import com.cse_411_project.aigy.khanh.model.UserModel
import com.cse_411_project.aigy.khanh.repositories.UserRepository

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _users: MutableLiveData<List<UserModel>> = MutableLiveData()
    val users: LiveData<List<UserModel>> = _users
    private val _selectedUser: MutableLiveData<UserModel?> = MutableLiveData()

    fun getUserByEmail(name: String) {
        userRepository.getUserByEmail(name,
            onComplete = { user ->
                _selectedUser.value = user
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

    fun getPhoneNumberByEmail(email: String) {
        userRepository.getPhoneNumberByEmail(email,
            onComplete = { phoneNumber ->
                _selectedUser.value?.phoneNumber ?: phoneNumber
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun updateUser(user: UserModel) {
        userRepository.updateUser(user,
            onComplete = {
                Log.d("loi" , "Cập nhật user thành công")
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun updatePasswordByEmailAndPhoneNumber(email: String, phoneNumber: String, password: String){
        userRepository.updatePasswordByEmailAndPhoneNumber(email, phoneNumber, password,
            onComplete = {
                Log.d("loi" , "Cập nhật mật khẩu thành công")
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun createUser(user: UserModel) {
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