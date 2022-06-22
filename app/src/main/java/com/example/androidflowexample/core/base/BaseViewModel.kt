package com.example.androidflowexample.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidflowexample.core.utils.Resource
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    private val _showLoading = MutableStateFlow<Boolean>(false)
    val showLoading = _showLoading.asSharedFlow()

    private val _networkError = MutableStateFlow<Throwable?>(null)
    val networkError = _networkError.asStateFlow()

    var loadingCount = 0

    fun <T> makeApiCall(
        onFailure: (Throwable?) -> Unit,
        onLoading: () -> Unit,
        onSuccess: (T?) -> Unit,
        showLoading: Boolean = true,
        request: suspend () -> Flow<Resource<T>>,
    ) {
        viewModelScope.launch {
            request().collect { apiResult ->
                when (apiResult) {
                    is Resource.Failure -> {
                        hideLoading()
                        _networkError.emit(apiResult.throwable)
                        onFailure.invoke(apiResult.throwable)
                    }
                    is Resource.Loading -> {
                        if (showLoading) {
                            showLoading()
                        }
                        onLoading.invoke()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        onSuccess.invoke(apiResult.value)
                    }
                }

            }
        }
    }

    private fun showLoading() {
        loadingCount++
        if (loadingCount > 0) {
            _showLoading.value = true
        }
    }

    private fun hideLoading() {
        loadingCount--
        if (loadingCount == 0) {
            _showLoading.value = false
        }
    }
}