package com.example.movieapptask.di

import android.content.Context
import com.example.data.datasource.local.MovieDatabase
import com.example.data.datasource.local.dao.MovieDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(
        @ApplicationContext context: Context
    ): MovieDatabase =
        MovieDatabase.getInstance(context = context)

    @Provides
    @Singleton
    fun provideMovieDao(
        database: MovieDatabase
    ): MovieDao =
        database.movieDao

//    @OptIn(ExperimentalPagingApi::class)
//    @Provides
//    fun provideMoviePager(service: MovieService, db: MovieDatabase): Pager<Int, MovieEntity> {
//        return Pager(
//            config = PagingConfig(pageSize = 20),
//            remoteMediator = MovieRemoteMediator(
//                service = service,
//                db = db
//            ),
//            pagingSourceFactory = {
//                db.movieDao.pagingSource()
//            }
//        )
//    }

}