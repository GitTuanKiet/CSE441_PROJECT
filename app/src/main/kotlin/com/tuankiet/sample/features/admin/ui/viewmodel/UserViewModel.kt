package com.tuankiet.sample.features.admin.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tuankiet.sample.core.failure.Failure
import com.tuankiet.sample.core.platform.BaseViewModel
import com.tuankiet.sample.features.admin.data.models.UserModel
import com.tuankiet.sample.features.admin.data.repositorys.UserRepository

class UserViewModel(private val userRepository: UserRepository) : BaseViewModel() {
    private val _users: MutableLiveData<List<UserModel>> = MutableLiveData()
    val users: LiveData<List<UserModel>> = _users
    private val _selectedUser: MutableLiveData<UserModel?> = MutableLiveData()
    val selectedUser: LiveData<UserModel?> = _selectedUser
    fun fetchUsers() {
        userRepository.getUsers(
            onComplete = { userList ->
                _users.value = userList
            },
            onError = { error ->
                handleFailure(Failure.DatabaseError)
                Log.e("loi", "Error fetching users: ${error.message}")
            }
        )
    }

    fun getUserByName(name: String) {
        userRepository.getUserByName(name,
            onComplete = { user ->
                _users.value = user
            },
            onError = { error ->
                handleFailure(Failure.DatabaseError)
            }
        )
    }
    fun getUserByUID(uid: String) {
        userRepository.getUserByUID(uid,
            onComplete = { user ->
                _selectedUser.value = user
            },
            onError = { error ->
                handleFailure(Failure.DatabaseError)
            }
        )
    }
    fun updateUser(user: UserModel) {
        userRepository.updateUser(user,
            onComplete = {
                // Handle successful update if needed
            },
            onError = { error ->
                handleFailure(Failure.DatabaseError)
            }
        )
    }

    fun deleteUser(uid: String) {
        userRepository.deleteUser(uid,
            onComplete = {
                Log.d("loi", "User deleted successfully")
            },
            onError = { error ->
                handleFailure(Failure.DatabaseError)
            }
        )
    }
    fun createAgent(userId: String , agentId: String) {
        userRepository.createAgent(userId , agentId, {}, { error ->
            Log.e("loi", "Lỗi khi tạo agent: ${error.message}")
        })
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
    fun createConversation(id : String ,userId : String , agentId : String) {
        userRepository.createConversation(id,userId, agentId,
            onComplete = {
                Log.d("loi" , "Tạo conversation thành công")
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }
    fun createMessage(id : String , conversationId : String ,senderId : String , content : String , type : String , completionTime : Long , timestamp : Long){
        userRepository.createMessage(id, conversationId ,senderId, content, type, completionTime, timestamp,
            onComplete = {
                Log.d("loi" , "Tạo tin nhắn thành công")
            },
            onError = {
                handleFailure(Failure.DatabaseError)
            }
        )
    }
}