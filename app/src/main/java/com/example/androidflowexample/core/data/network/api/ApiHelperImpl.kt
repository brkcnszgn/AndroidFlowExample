package com.example.androidflowexample.core.data.network.api

import com.example.androidflowexample.core.data.network.model.responses.MovieDetailApiModel
import com.example.androidflowexample.core.data.network.model.responses.MovieApiModel
import retrofit2.Response
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getNowPlayingMovieList(): Response<MovieApiModel> =
        apiService.getNowPlayingMovieList()

    override suspend fun getUpcomingMovieList(page:Int): Response<MovieApiModel> =
        apiService.getUpcomingMovieList(page)

    override suspend fun getMovieDetail(id: Int): Response<MovieDetailApiModel> =
        apiService.getMovieDetail(id)
}