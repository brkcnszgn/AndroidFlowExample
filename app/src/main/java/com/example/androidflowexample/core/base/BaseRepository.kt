package com.example.androidflowexample.core.base

import com.example.androidflowexample.core.utils.DispatcherProvider
import com.example.androidflowexample.core.utils.NetworkHelper
import com.example.androidflowexample.core.utils.NetworkResource
import com.example.androidflowexample.core.utils.ResourceProvider
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

abstract class BaseRepository constructor(
    private val networkHelper: NetworkHelper,
    private val resourceProvider: ResourceProvider,
    private val dispatcher: DispatcherProvider
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ): NetworkResource<T> {
        return withContext(dispatcher.io) {
            if (networkHelper.isNetworkConnected()) {
                try {
                    val result = apiCall.invoke()
                    when ((result as Response<*>).code()) {
                        in 200..300 -> NetworkResource.Success(result)
                        401 -> NetworkResource.Error(resourceProvider.getString(Str.auth_exception))
                        else -> NetworkResource.Error(result.message())
                    }
                } catch (throwable: Throwable) {
                    ProgressBar.newInstance().hide()
                    when (throwable) {
                        is HttpException -> NetworkResource.Error(throwable.message())
                        is SocketTimeoutException -> NetworkResource.Error(
                            resourceProvider.getString(
                                Str.socket_exception
                            )
                        )
                        is IOException -> NetworkResource.Error(resourceProvider.getString(Str.io_exception))
                        else -> NetworkResource.Error(resourceProvider.getString(Str.unexpected_error))
                    }
                }

            } else {
                ProgressBar.newInstance().hide()
                NetworkResource.Error(resourceProvider.getString(Str.no_internet_connection))
            }
        }
    }
}