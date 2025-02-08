package com.example.State

sealed class MainVideoAppState<out T> {
    object loading : MainVideoAppState<Nothing>()
    data class Success<out T>(val data: T) : MainVideoAppState<T>()
    data class Error(val exception: Throwable) : MainVideoAppState<Nothing>()
}