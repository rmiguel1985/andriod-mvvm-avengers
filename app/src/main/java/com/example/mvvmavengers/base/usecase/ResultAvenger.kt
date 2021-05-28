package com.example.mvvmavengers.base.usecase

/**
 * ResultAvenger Class
 *
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
sealed class ResultAvenger<out R> {

    data class Success<out T>(val data: T) : ResultAvenger<T>()
    data class Error(val exception: Exception) : ResultAvenger<Nothing>()
    object Loading : ResultAvenger<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[exception=$exception]"
            Loading -> "Loading"
        }
    }
}

val <T> ResultAvenger<T>.data: T?
    get() = (this as? ResultAvenger.Success)?.data

val <Exception> ResultAvenger<Exception>.errorMessage: String?
    get() = (this as? ResultAvenger.Error)?.exception?.message
