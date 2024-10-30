package com.cse_411_project.aigy.khanh.repositories

import android.util.Log
import com.cse_411_project.aigy.features.admin.data.models.UserModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.GenericTypeIndicator
import com.google.firebase.database.ValueEventListener

class UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")
    // Map lại dữ liệu
    fun getData(userSnapshot: DataSnapshot) : UserModel {
        val uid = userSnapshot.child("uid").getValue(String::class.java)
        val name = userSnapshot.child("name").getValue(String::class.java)
        val email = userSnapshot.child("email").getValue(String::class.java)
        val phone = userSnapshot.child("phone").getValue(String::class.java)
        val urlImg = userSnapshot.child("urlImg").getValue(String::class.java)
        val decentralization = userSnapshot.child("decentralization").getValue(String::class.java)
        val isOnline = userSnapshot.child("online").getValue(Boolean::class.java)
        val idListAgent = userSnapshot.child("idListAgent").getValue(object : GenericTypeIndicator<MutableList<String>>() {}) ?: mutableListOf()
        val listVisit = userSnapshot.child("listVist").getValue(object : GenericTypeIndicator<MutableList<Long>>() {}) ?: mutableListOf()
        val user = UserModel(uid ?: "", name ?: "", email ?: "", phone ?: "", urlImg ?: "", decentralization ?: "", isOnline ?: false, idListAgent , listVisit)
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

    // Tìm người dùng theo tên
    fun getUserByName(name: String, onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val matchingUsers = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user.let {
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

    // Tìm người dùng theo UID
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

    // Tìm người dùng theo email
    fun getUserByEmail(email: String, onComplete: (UserModel?) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var foundUser: UserModel? = null

                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user.let{
                        if (user.email == email) {
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

    // Chỉnh sửa thông tin người dùng
    fun updateUser(user: com.cse_411_project.aigy.khanh.model.UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(user.uid).setValue(user)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
    }

    // Xóa người dùng
    fun deleteUser(uid: String, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.orderByChild("uid").equalTo(uid).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val userSnapshot = snapshot.children.first()
                    val userKey = userSnapshot.key
                    userKey?.let { key ->
                        usersRef.child(key).removeValue()
                            .addOnSuccessListener {
                                onComplete()
                            }
                            .addOnFailureListener { e ->
                                onError(DatabaseError.fromException(e))
                            }
                    }
                } else {
                    Log.d("loi", "Người dùng không tồn tại")
                }
            }
            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    // Tạo người dùng
    fun createUser(userModel: com.cse_411_project.aigy.khanh.model.UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        val db = FirebaseDatabase.getInstance().getReference("users")
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var isException = false
                for (data in snapshot.children){
                    val user = getData(data)
                    if (user.uid == userModel.uid){
                        isException = true
                        break
                    }
                }
                if(!isException){
                    db.child(userModel.uid).setValue(userModel)
                        .addOnSuccessListener { onComplete() }
                        .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    fun fetchVisit(onComplete: (List<List<Long>>) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                val userVisits = mutableListOf<List<Long>>()
                for (data in snapshot.children) {
                    val user = getData(data)
                    user.let {
                        userVisits.add(it.listVist)
                    }
                }
                onComplete(userVisits)
            } else {
                onComplete(emptyList())
            }
        }
            .addOnFailureListener { e ->
                onError(DatabaseError.fromException(e))
            }
    }
}
