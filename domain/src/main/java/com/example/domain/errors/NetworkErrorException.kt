package com.example.domain.errors

import retrofit2.HttpException

class NetworkErrorException(
    val errorCode: Int = -1,
    errorMessage: String = "Network error occurred!"
) : Throwable(errorMessage) {

    companion object {
        fun parseException(httpException: HttpException): NetworkErrorException {
            val errorCode = httpException.code()
            val errorMessage = httpException.message() ?: "Unknown error occurred"
            return NetworkErrorException(errorCode, errorMessage)
        }
    }
}