package com.tuankiet.sample.features.admin.data

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserRepository {
    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    fun getUsers(onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userList = mutableListOf<UserModel>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UserModel::class.java)
                    user?.let { userList.add(it) }
                }
                onComplete(userList)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    fun getUserByUID(uid : String , onComplete: (UserModel) -> Unit , onError: (DatabaseError) -> Unit){
        usersRef.child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UserModel::class.java)
                user?.let { onComplete(it) }
            }
            override fun onCancelled(error: DatabaseError) {
                onError(error)
            }
        })
    }

    fun updateUser(user: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(user.UID).setValue(user)
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }
    }

    fun deleteUser(uid: String, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        usersRef.child(uid).removeValue()
            .addOnSuccessListener { onComplete() }
            .addOnFailureListener { e -> onError(DatabaseError.fromException(e)) }

    }
}