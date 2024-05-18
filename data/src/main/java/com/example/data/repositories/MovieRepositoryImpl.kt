package com.example.data.repositories

import com.example.data.datasource.local.dao.MovieDao
import com.example.data.datasource.remote.service.MovieService
import com.example.data.mapper.toDomain
import com.example.data.mapper.toEntity
import com.example.domain.models.Movie
import com.example.domain.models.MovieCategory
import com.example.domain.repositories.MoviesRepository

class MovieRepositoryImpl(
    private val service: MovieService,
    private val movieDao: MovieDao
) : MoviesRepository {
    override suspend fun getMoviesByCategory(category: MovieCategory): List<Movie> {
        return movieDao.getMovieByCategory(category = category).map { it.toDomain() }
    }

    override suspend fun getAllMovies(): List<Movie> {
       return movieDao.getAll().map { it.toDomain() }
    }

    override suspend fun getMoviesById(movieId: Int): Movie {
        return movieDao.getMovieByID(movieId = movieId).toDomain()
    }

    override suspend fun refresh() {
        MovieCategory.entries.forEach { category: MovieCategory ->
            val response = service.getMoviesByCategory(category = category.endPoint)
            val movieEntity = response.results.map { it.toEntity(movieCategory = category) }
            movieDao.upsertAll(movieEntity)
        }
    }
}