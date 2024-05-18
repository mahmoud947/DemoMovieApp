package com.example.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.datasource.local.entity.MovieEntity
import com.example.domain.models.Movie
import com.example.domain.models.MovieCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertAll(movies:List<MovieEntity>)
    @Query("SELECT * FROM movieentity")
    fun pagingSource():PagingSource<Int,MovieEntity>

    @Query("SELECT * FROM movieentity WHERE category=:category")
    suspend fun getMovieByCategory(category: MovieCategory):List<Movie>

    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}