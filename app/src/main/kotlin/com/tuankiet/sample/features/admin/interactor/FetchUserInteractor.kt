package com.tuankiet.sample.features.admin.interactor

import com.google.firebase.database.DatabaseError
import com.tuankiet.sample.features.admin.data.UserModel
import com.tuankiet.sample.features.admin.data.UserRepository

class FetchUsersInteractor(private val userRepository: UserRepository) {
    fun execute(onComplete: (List<UserModel>) -> Unit, onError: (DatabaseError) -> Unit) {
        userRepository.getUsers(onComplete, onError)
    }
}