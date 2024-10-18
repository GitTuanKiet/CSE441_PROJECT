package com.tuankiet.sample.features.admin.interactor

import com.google.firebase.database.DatabaseError
import com.tuankiet.sample.features.admin.data.UserRepository

class DeleteUserInteractor(private val userRepository: UserRepository) {
    fun execute(uid: String, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        userRepository.deleteUser(uid, onComplete, onError)
    }
}
