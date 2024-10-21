package com.tuankiet.sample.features.admin.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    fun getData(userSnapshot: DataSnapshot) : UserModel{
        val uid = userSnapshot.child("uid").getValue(String::class.java)
        val name = userSnapshot.child("name").getValue(String::class.java)
        val email = userSnapshot.child("email").getValue(String::class.java)
        val phone = userSnapshot.child("phone").getValue(String::class.java)
        val urlImg = userSnapshot.child("urlImg").getValue(String::class.java)
        val decentralization = userSnapshot.child("decentralization").getValue(String::class.java)
        val isOnline = userSnapshot.child("isOnline").getValue(Boolean::class.java)
        val user = UserModel(uid ?: "", name ?: "", email ?: "", phone ?: "", urlImg ?: "", decentralization ?: "", isOnline ?: false, listOf()  )
        return user
    }
    // Lấy tất cả danh sách người dùng từ fire base
    fun getUsers(onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    userList.add(getData(userSnapshot))
                }
                onComplete(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }


    fun getUserByName(name: String, onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val matchingUsers = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user?.let {
                        // Kiểm tra xem tên người dùng có chứa chuỗi tìm kiếm không (không phân biệt chữ hoa/thường)
                        if (it.name.contains(name, ignoreCase = true)) {
                            matchingUsers.add(it)
                        }
                    }
                }
                // Trả về danh sách người dùng khớp với tên tìm kiếm
                onComplete(matchingUsers) // Trả về một danh sách List<UserModel>
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }
    fun getUserByUID(uid: String, onComplete: (UserModel?) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var foundUser: UserModel? = null

                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user.let{
                        if (user.uid == uid) {
                            foundUser = user
                        }
                    }
                }
                onComplete(foundUser)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    fun updateUser(user: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(user.uid).setValue(user)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
    }

    fun deleteUser(uid: String, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(uid).removeValue()
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }

    }
}