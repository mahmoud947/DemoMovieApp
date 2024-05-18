package com.example.movieapptask.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.example.data.datasource.local.dao.MovieDao
import javax.inject.Inject


class CleanupMoviesWorkerFactory @Inject constructor(private val movieDao: MovieDao) :
    WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker =
        CleanupMoviesWorker(context = appContext, params = workerParameters, movieDao = movieDao)
}
