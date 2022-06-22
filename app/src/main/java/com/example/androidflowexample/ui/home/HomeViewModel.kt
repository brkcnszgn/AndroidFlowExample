package com.example.androidflowexample.ui.home


import androidx.lifecycle.viewModelScope
import com.example.androidflowexample.core.base.BaseViewModel
import com.example.androidflowexample.ui.MainRepository
import com.example.androidflowexample.ui.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private var _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.IDLE)
    var homeUiState = _homeUiState.asStateFlow()

    init {
        getMovie()
    }

    private fun getMovie() {
        makeApiCall(
            onFailure = { error ->
                viewModelScope.launch {
                    _homeUiState.emit(HomeUiState.Error(error?.message.orEmpty()))
                }
            },
            onLoading = {

            },
            onSuccess = { apiResult ->
                viewModelScope.launch {
                    _homeUiState.emit(HomeUiState.Success(apiResult?.movieOverviews?.toMutableList().orEmpty().toMutableList()))
                }

            },
            request = {
                mainRepository.getNowPlayingMovieList()
            }
        )
    }

}