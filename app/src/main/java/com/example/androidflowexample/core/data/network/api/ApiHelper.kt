package com.example.androidflowexample.core.data.network.api

import com.example.androidflowexample.core.data.network.model.responses.MovieDetailApiModel
import com.example.androidflowexample.core.data.network.model.responses.MovieApiModel
import retrofit2.Response

interface ApiHelper {

    suspend fun getNowPlayingMovieList(): Response<MovieApiModel>
    suspend fun getUpcomingMovieList(page:Int): Response<MovieApiModel>
    suspend fun getMovieDetail(id: Int): Response<MovieDetailApiModel>
}