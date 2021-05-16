package com.revnarayan.rogerstimburtonsassignment.network

sealed class Try<out T> {
    data class Success<T>(val value: T) : Try<T>()
    data class Failure<T>(val throwable: T) : Try<Nothing>()
}
