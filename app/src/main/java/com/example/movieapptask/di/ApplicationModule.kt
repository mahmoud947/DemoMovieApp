package com.example.movieapptask.di

import androidx.paging.Pager
import com.example.data.datasource.local.dao.MovieDao
import com.example.data.datasource.local.entity.MovieEntity
import com.example.data.datasource.remote.service.MovieService
import com.example.data.repositories.MovieRepositoryImpl
import com.example.domain.repositories.MoviesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {
    @Provides
    fun provideMovieRepository(service: MovieService,dao: MovieDao): MoviesRepository =
        MovieRepositoryImpl(
            service = service,
            movieDao = dao
        )
}