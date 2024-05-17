package com.example.data.datasource.remote.service

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("movie/{category}")
    suspend fun getMoviesByCategory(
        @Path("category")
        category:String,
        @Query("page")
        page:Int
    )
}