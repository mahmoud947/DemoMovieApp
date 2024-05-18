package com.example.movieapptask.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.data.datasource.local.dao.MovieDao
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val TAG = "CleanupMoviesWorker"
@HiltWorker
class CleanupMoviesWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val movieDao: MovieDao
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO)
    {
        try {
            movieDao.clearAll()
            Result.success()
        } catch (e: Exception) {
            Log.d(TAG, "doWork: $e")
            Result.failure()
        }
    }
}
