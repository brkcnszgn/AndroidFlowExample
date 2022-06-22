package com.example.androidflowexample.core.data.network.api

import com.example.androidflowexample.core.data.network.model.responses.MovieDetailResponse
import com.example.androidflowexample.core.data.network.model.responses.MovieListResponse
import retrofit2.Response

interface ApiHelper {

    suspend fun getNowPlayingMovieList(): Response<MovieListResponse>
    suspend fun getUpcomingMovieList(page:Int): Response<MovieListResponse>
    suspend fun getMovieDetail(id: Int): Response<MovieDetailResponse>
}