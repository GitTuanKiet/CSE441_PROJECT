package com.cse_411_project.aigy.features.chat.data

sealed class Result<T> {
    class Success<T>(val data: T) : Result<T>()
    class Error<T>(val exception: Exception) : Result<T>()
}