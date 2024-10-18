package com.tuankiet.sample.features.admin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseError
import com.tuankiet.sample.core.failure.Failure
import com.tuankiet.sample.core.platform.BaseViewModel
import com.tuankiet.sample.features.admin.data.UserModel
import com.tuankiet.sample.features.admin.interactor.DeleteUserInteractor
import com.tuankiet.sample.features.admin.interactor.FetchUsersInteractor
import com.tuankiet.sample.features.admin.interactor.GetUserByUIDInteractor
import com.tuankiet.sample.features.admin.interactor.UpdateUserInteractor

class UserViewModel (
    private val fetchUsersInteractor: FetchUsersInteractor,
    private val deleteUserInteractor: DeleteUserInteractor,
    private val getUserByUIDInteractor: GetUserByUIDInteractor,
    private val updateUserInteractor: UpdateUserInteractor
) : BaseViewModel() {

    private val _users: MutableLiveData<List<UserModel>> = MutableLiveData()
    val users: LiveData<List<UserModel>> = _users

    private val _user: MutableLiveData<UserModel?> = MutableLiveData()
    val user: LiveData<UserModel?> = _user

    // Xử lý logic lấy danh sách người dùng
    fun fetchUsers() {
        fetchUsersInteractor.execute(
            onComplete = { userList ->
                _users.value = userList
            },
            onError = { error ->
                handleFirebaseError(error)
            }
        )
    }

    // Xử lý logic xóa người dùng
    fun deleteUser(uid: String) {
        deleteUserInteractor.execute(
            uid,
            onComplete = {
                fetchUsers() // Cập nhật lại danh sách sau khi xóa
            },
            onError = { error ->
                handleFirebaseError(error)
            }
        )
    }

    // Tìm kiếm người dùng theo UID
    fun getUserByUID(uid: String) {
        getUserByUIDInteractor.execute(
            uid,
            onComplete = { foundUser ->
                _user.value = foundUser
            },
            onError = { error ->
                handleFirebaseError(error)
            }
        )
    }

    // Cập nhật thông tin người dùng
    fun updateUser(user: UserModel) {
        updateUserInteractor.execute(
            user,
            onComplete = {
                // Cập nhật lại danh sách người dùng sau khi chỉnh sửa thành công
                fetchUsers()
            },
            onError = { error ->
                handleFirebaseError(error)
            }
        )
    }

    // Xử lý lỗi Firebase và chuyển đổi sang Failure
    private fun handleFirebaseError(error: DatabaseError) { // Đưa ra ngoài các phương thức
        val failure = when (error.code) {
            DatabaseError.DISCONNECTED -> Failure.NetworkConnection
            else -> Failure.ServerError
        }
        handleFailure(failure) // Gọi hàm xử lý thất bại của BaseViewModel
    }
}
