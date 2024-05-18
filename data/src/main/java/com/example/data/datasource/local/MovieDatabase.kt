package com.example.data.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.data.constants.Constants.DATABASE_NAME
import com.example.data.datasource.local.converters.Converters
import com.example.data.datasource.local.dao.MovieDao
import com.example.data.datasource.local.entity.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract val movieDao: MovieDao

    companion object {
        private lateinit var INSTANCE: MovieDatabase
        fun getInstance(context: Context): MovieDatabase {
            synchronized(this) {
                if (!::INSTANCE.isInitialized) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MovieDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}