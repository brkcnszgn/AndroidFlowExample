package com.example.androidflowexample.ui.home.model

import com.example.androidflowexample.core.data.network.model.common.MovieOverview

sealed class HomeUiState(
    open var movieList: MutableList<MovieOverview>? = null
) {

    object IDLE : HomeUiState()

    data class Success(override var movieList: MutableList<MovieOverview>?) : HomeUiState(movieList)
    data class RefreshList(var refreshMovieList: MutableList<MovieOverview>) :
        HomeUiState(refreshMovieList)

    data class Error(var message: String) : HomeUiState()


}
