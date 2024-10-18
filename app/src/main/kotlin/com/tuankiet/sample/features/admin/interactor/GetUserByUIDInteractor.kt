package com.tuankiet.sample.features.admin.interactor

import com.google.firebase.database.DatabaseError
import com.tuankiet.sample.features.admin.data.UserModel
import com.tuankiet.sample.features.admin.data.UserRepository

class GetUserByUIDInteractor(private val userRepository: UserRepository) {
    fun execute(uid: String, onComplete: (UserModel?) -> Unit, onError: (DatabaseError) -> Unit) {
        userRepository.getUserByUID(uid, onComplete, onError)
    }
}
