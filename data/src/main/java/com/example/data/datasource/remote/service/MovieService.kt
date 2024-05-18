package com.example.data.datasource.remote.service

import com.example.data.datasource.remote.dtos.response.BaseResponse
import com.example.data.datasource.remote.dtos.response.MovieRes
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/{category}")
    suspend fun getMoviesByCategory(
        @Path("category")
        category:String
    ):BaseResponse<List<MovieRes>>
}