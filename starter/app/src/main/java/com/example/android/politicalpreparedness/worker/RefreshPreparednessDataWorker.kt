package com.example.android.politicalpreparedness.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.android.politicalpreparedness.database.ElectionDatabase
import com.example.android.politicalpreparedness.repository.ElectionRepository
import retrofit2.HttpException


class RefreshPreparednessDataWorker(
    context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            val databaseImpl = ElectionDatabase(applicationContext)
            val repository = ElectionRepository(databaseImpl)
            repository.fetchElectionData()
            Result.success()
        } catch (ex: HttpException) {
            Result.retry()
        }
    }
}