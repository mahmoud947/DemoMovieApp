package com.example.domain.models


import androidx.annotation.Keep

@Keep
data class Movie(
    val adult: Boolean,
    val backdropUrl: String,
    val genreIds: List<Int?>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterUrl: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)