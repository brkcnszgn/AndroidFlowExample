package com.example.androidflowexample.core.utils


sealed class NetworkResource<T>(val data: T?, var loading: Boolean, val message: String) {
    class Success<T>(data: T?) : NetworkResource<T>(data, false, "Success")
    class Error<T>(message: String) : NetworkResource<T>(null, false, message)
}