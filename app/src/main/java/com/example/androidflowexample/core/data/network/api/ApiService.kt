package com.example.androidflowexample.core.data.network.api

import com.example.androidflowexample.core.data.network.model.responses.MovieDetailApiModel
import com.example.androidflowexample.core.data.network.model.responses.MovieApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("now_playing")
    suspend fun getNowPlayingMovieList(): Response<MovieApiModel>

    @GET("upcoming")
    suspend fun getUpcomingMovieList(@Query("page") page: Int): Response<MovieApiModel>

    @GET("{id}")
    suspend fun getMovieDetail(@Path("id") movieId: Int): Response<MovieDetailApiModel>

}