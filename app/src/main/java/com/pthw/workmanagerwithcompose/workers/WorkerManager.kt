package com.pthw.workmanagerwithcompose.workers

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * Created by P.T.H.W on 13/08/2024.
 */

class WorkerManager(private val context: Context) {

    fun startDownloadWorker() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()

        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("item" to "HelloWorld"))
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniqueWork(
            "unique_one_time_worker",
            ExistingWorkPolicy.REPLACE,
            workRequest
        )

//        workManager.getWorkInfoByIdLiveData(workRequest.id).observe(context) { workInfo ->
//            if (workInfo.state == WorkInfo.State.SUCCEEDED) {
//                // Handle successful completion
//
//                val outputData = workInfo.outputData
//                Log.d("MyWorker", outputData.getString("item").toString())
//            }
//        }
    }

    fun startPeriodicWorker() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()


        val periodicWorkRequest = PeriodicWorkRequestBuilder<PeriodicWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .setInputData(workDataOf("item" to "HelloWorld"))
            .build()

        val workManager = WorkManager.getInstance(context)

        workManager.enqueueUniquePeriodicWork(
            "unique_periodic_worker",
            ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
            periodicWorkRequest
        )
    }

    fun startChainDownloadWorkers() {
        val constraints = Constraints.Builder()
            .setRequiresStorageNotLow(true)
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(false)
            .build()
        val workRequest = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("item" to "HelloWorld"))
            .build()
        val workRequest2 = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setConstraints(constraints)
            .setInputData(workDataOf("item" to "HelloWorld"))
            .build()
        val workManager = WorkManager.getInstance(context)

        workManager.beginUniqueWork(
            "unique_one_time_worker_chain",
            ExistingWorkPolicy.REPLACE,
            workRequest
        ).then(workRequest2).enqueue()
    }
}