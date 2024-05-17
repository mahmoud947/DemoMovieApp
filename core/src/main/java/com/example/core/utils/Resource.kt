package com.example.core.utils

sealed class Resource<out T>() {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable? = null) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data class ErrorWithMessage(val errorMessage: String) : Resource<Nothing>()

    fun isSuccessful(): Boolean = this is Success
    fun isFailed(): Boolean = this is Error
    fun isLoading(): Boolean = this is Loading

    fun getMessage(): String {
        return if (this is ErrorWithMessage) {
            this.errorMessage
        } else {
            "Un known Error"
        }
    }

    fun isEmpty(): Boolean {
        if (this is Success) {
            return if (this.data == null) {
                true
            } else {
                this.data is List<*> && this.data.isEmpty()
            }
        }
        return true
    }
}

fun <T> Resource<T>.getData(): T? {
    if (this is Resource.Success)
        return this.data
    return null
}