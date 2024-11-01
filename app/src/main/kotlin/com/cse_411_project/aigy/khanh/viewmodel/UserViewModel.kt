package com.cse_411_project.aigy.khanh.viewmodel

import android.util.Log
import com.cse_411_project.aigy.khanh.core.failure.Failure
import com.cse_411_project.aigy.khanh.model.UserModel
import com.cse_411_project.aigy.khanh.repositories.UserRepository

class UserViewModel(private val userRepository: UserRepository) {
    private var _user: UserModel? = null

    fun getUserByEmail(name: String, onComplete: (UserModel?) -> Unit) {
        userRepository.getUserByEmail(name,
            onComplete = { user ->
                _user = user
                if (user != null) {
                    onComplete(user)
                }
            },
            onError = {
                Log.d("loi" , "Lấy user thất bại")
                onComplete(null)
            }
        )
    }

    fun getUserByUID(uid: String, onComplete: (UserModel?) -> Unit) {
        userRepository.getUserByUID(uid,
            onComplete = { user ->
                _user = user
                if (user != null) {
                    onComplete(user)
                }
            },
            onError = {
                Log.d("loi" , "Lấy user thất bại")
                onComplete(null)
            }
        )
    }

    fun getPhoneNumberByEmail(email: String, onComplete: (String) -> Unit) {
        userRepository.getPhoneNumberByEmail(email,
            onComplete = { phoneNumber ->
                _user?.phoneNumber ?: phoneNumber
                if (phoneNumber != null) {
                    onComplete(phoneNumber)
                }
            },
            onError = {
                Log.d("loi" , "Lấy số điện thoại thất bại")
                onComplete("")
            }
        )
    }

    fun updateUser(user: UserModel, onComplete: (Boolean) -> Unit) {
        userRepository.updateUser(user,
            onComplete = {
                Log.d("loi" , "Cập nhật user thành công")
                onComplete(true)
            },
            onError = {
                Log.d("loi" , "Cập nhật user thất bại")
                onComplete(false)
            }
        )
    }

    fun updatePasswordByEmailAndPhoneNumber(email: String, phoneNumber: String, password: String, onComplete: (Boolean) -> Unit) {
        userRepository.updatePasswordByEmailAndPhoneNumber(email, phoneNumber, password,
            onComplete = {
                Log.d("loi" , "Cập nhật mật khẩu thành công")
                onComplete(true)
            },
            onError = {
                Log.d("loi" , "Cập nhật mật khẩu thất bại")
                onComplete(false)
            }
        )
    }

    fun createUser(user: UserModel, onComplete: (Boolean) -> Unit) {
        userRepository.createUser(user,
            onComplete = {
                Log.d("loi" , "Tạo user thành công")
                onComplete(true)
            },
            onError = {
                Log.d("loi" , "Tạo user thất bại")
                onComplete(false)
            }
        )
    }
}