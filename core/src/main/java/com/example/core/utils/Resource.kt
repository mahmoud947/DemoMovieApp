package com.example.core.utils

sealed class Resource<out T>() {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val exception: Throwable) : Resource<Nothing>()
    data object Loading : Resource<Nothing>()
    data object Idle: Resource<Nothing>()

    fun isSuccessful(): Boolean = this is Success
    fun isFailed(): Boolean = this is Error
    fun isLoading(): Boolean = this is Loading


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

fun <T>Resource<T>.handle(
    onLoading : () -> Unit,
    onSuccess : (T) -> Unit,
    onError: (Throwable) -> Unit
) {
    when(this) {
        is Resource.Error -> onError(exception)
        Resource.Idle -> {}
        Resource.Loading -> onLoading()
        is Resource.Success -> onSuccess(data)
    }
}