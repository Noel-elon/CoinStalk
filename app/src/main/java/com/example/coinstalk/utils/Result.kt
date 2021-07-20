package com.example.coinstalk.utils

import java.io.IOException
import java.net.UnknownHostException

sealed class Result<out R> {
    data class Success<out T>(val data: T? = null) : Result<T>()

    data class Error(val error: Throwable) : Result<Nothing>() {
        val errorMessage: String
            get() = when (error) {
                is UnknownHostException -> {
                    "Check your internet connection"
                }
                is IOException -> {
                    "Your network might be slow"
                }
                else -> {
                    "Oops, Something went wrong"
                }
            }
    }

    object Loading : Result<Nothing>()
}