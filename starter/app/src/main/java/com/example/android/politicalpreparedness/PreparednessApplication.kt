package com.example.android.politicalpreparedness

import android.annotation.SuppressLint
import android.app.Application
import android.content.SharedPreferences
import android.os.Build
import androidx.work.*
import com.example.android.politicalpreparedness.sharedManager.SharedPreferenceManager
import com.example.android.politicalpreparedness.util.Constants.WORK_NAME
import com.example.android.politicalpreparedness.worker.RefreshPreparednessDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class PreparednessApplication : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)
    override fun onCreate() {
        super.onCreate()
        SharedPreferenceManager.instance(this)
        delayInit()
    }

    private fun delayInit(): Unit = with(applicationScope) {
        launch {
            setupRecurringWork()
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun setupRecurringWork() {
        val constraint=Constraints.Builder()
            .setRequiredNetworkType(NetworkType.METERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .apply {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setRequiresDeviceIdle(true)
                }
            }.build()

        val relatingRequest= PeriodicWorkRequestBuilder<RefreshPreparednessDataWorker>(1,TimeUnit.DAYS)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            relatingRequest
        )
    }
}