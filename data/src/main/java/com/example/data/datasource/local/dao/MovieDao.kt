package com.example.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.datasource.local.entity.MovieEntity

@Dao
interface MovieDao {
    @Upsert
    suspend fun upsertAll(movies:List<MovieEntity>)
    @Query("SELECT * FROM movieentity")
    fun pagingSource():PagingSource<Int,MovieEntity>
    @Query("DELETE FROM movieentity")
    suspend fun clearAll()
}