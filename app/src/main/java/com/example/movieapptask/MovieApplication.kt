package com.example.movieapptask

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.movieapptask.worker.CleanupMoviesWorker
import com.example.movieapptask.worker.CleanupMoviesWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import java.util.Calendar
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class MovieApplication : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: CleanupMoviesWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()
        scheduleCleanupWork(this)
    }


    private fun scheduleCleanupWork(context: Context) {

        val workRequest = PeriodicWorkRequest.Builder(
            CleanupMoviesWorker::class.java,
            4, TimeUnit.HOURS,
            15, TimeUnit.MINUTES
        )
            .setConstraints(Constraints(requiresBatteryNotLow = true))
            .setInitialDelay(2000, TimeUnit.MILLISECONDS)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "CleanupVisitsWorker",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}