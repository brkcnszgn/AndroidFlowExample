package com.example.androidflowexample.ui

import com.example.androidflowexample.core.base.BaseRepository
import com.example.androidflowexample.core.data.di.DispatcherProvider
import com.example.androidflowexample.core.data.network.api.ApiHelper
import com.example.androidflowexample.core.utils.NetworkHelper
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val apiHelper: ApiHelper,
    networkHelper: NetworkHelper,
    dispatcher: DispatcherProvider
) : BaseRepository(networkHelper, dispatcher) {

    fun getNowPlayingMovieList() = baseRequestFlow { apiHelper.getNowPlayingMovieList() }

    fun getUpcomingMovieList(page: Int) =
        baseRequestFlow { apiHelper.getUpcomingMovieList(page) }

    fun getMovieDetail(id: Int) = baseRequestFlow { apiHelper.getMovieDetail(id) }
}