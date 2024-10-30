package com.cse_411_project.aigy.khanh.repositories

import android.util.Log
import com.cse_411_project.aigy.khanh.model.UserModel
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
        val fullName = userSnapshot.child("name").getValue(String::class.java)
        val email = userSnapshot.child("email").getValue(String::class.java)
        val password = userSnapshot.child("password").getValue(String::class.java)
        val phoneNumber = userSnapshot.child("phone").getValue(String::class.java)
        val urlImage = userSnapshot.child("urlImage").getValue(String::class.java)
        val decentralization = userSnapshot.child("decentralization").getValue(String::class.java)
        val isOnline = userSnapshot.child("online").getValue(Boolean::class.java)
        val idListAgent = userSnapshot.child("idListAgent").getValue(object : GenericTypeIndicator<MutableList<String>>() {}) ?: mutableListOf()
        val conversationList = userSnapshot.child("conversationList").getValue(object : GenericTypeIndicator<MutableList<String>>() {}) ?: mutableListOf()
        val referralCount = userSnapshot.child("referralCount").getValue(Int::class.java)

        val user = UserModel(
            uid = uid ?: "",
            fullName = fullName ?: "",
            email = email ?: "",
            password = password ?: "",
            phoneNumber = phoneNumber ?: "",
            urlImage = urlImage ?: "",
            decentralization = decentralization ?: "",
            isOnline = isOnline ?: false,
            idListAgent = idListAgent,
            conversationList = conversationList,
            referralCount = referralCount ?: 0
        )
        return user
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

    // Tìm số điện thoại của người dùng từ email
    fun getPhoneNumberByEmail(email: String, onComplete: (String?) -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var phoneNumber: String? = null

                for (userSnapshot in snapshot.children) {
                    val user = getData(userSnapshot)
                    user.let{
                        if (user.email == email) {
                            phoneNumber = user.phoneNumber
                        }
                    }
                }
                onComplete(phoneNumber)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    // Chỉnh sửa thông tin người dùng
    fun updateUser(user: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(user.uid).setValue(user)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
    }

    // Tạo người dùng
    fun createUser(userModel: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
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
}
