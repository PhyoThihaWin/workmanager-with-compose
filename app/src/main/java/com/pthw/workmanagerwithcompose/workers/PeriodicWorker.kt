package com.pthw.workmanagerwithcompose.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pthw.workmanagerwithcompose.data.CacheDataStoreRepository
import com.pthw.workmanagerwithcompose.utils.NotificationHandler
import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Created by P.T.H.W on 14/08/2024.
 */
class PeriodicWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val notification by lazy { NotificationHandler(applicationContext) }
    private val cacheDataStoreRepository by lazy { CacheDataStoreRepository(applicationContext) }

    override suspend fun doWork(): Result {
        notification.showNotification("Periodic Worker Updated!")
        cacheDataStoreRepository.updatePeriodicTime(getTime())

        return Result.success()
    }

    private fun getTime(): String {
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z")
        return simpleDateFormat.format(calendar.time).toString()
    }
}