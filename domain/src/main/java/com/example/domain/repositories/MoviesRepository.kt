package com.example.domain.repositories

import com.example.domain.models.Movie
import com.example.domain.models.MovieCategory

interface MoviesRepository {
    suspend fun getMoviesByCategory(
        category: MovieCategory,
    ): List<Movie>

    suspend fun refresh(category: MovieCategory)
}