package com.example.data.datasource.local.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.models.MovieCategory

@Entity
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val adult: Boolean?,
    val backdropUrl: String?,
    val genreIds: List<Int>,
    val originalLanguage: String?,
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    val posterUrl: String?,
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val category: String?
)