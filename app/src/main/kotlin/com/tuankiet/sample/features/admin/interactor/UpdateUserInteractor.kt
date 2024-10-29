package com.tuankiet.sample.features.admin.interactor

import com.google.firebase.database.DatabaseError
import com.tuankiet.sample.features.admin.data.UserModel
import com.tuankiet.sample.features.admin.data.UserRepository

class UpdateUserInteractor(private val userRepository: UserRepository) {
    fun execute(user: UserModel, onComplete: () -> Unit, onError: (DatabaseError) -> Unit) {
        userRepository.updateUser(user, onComplete, onError)
    }
}
