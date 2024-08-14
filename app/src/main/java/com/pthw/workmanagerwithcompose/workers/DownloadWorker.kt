package com.pthw.workmanagerwithcompose.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.pthw.workmanagerwithcompose.data.CacheDataStoreRepository
import com.pthw.workmanagerwithcompose.utils.NotificationHandler
import kotlinx.coroutines.delay


/**
 * Created by P.T.H.W on 12/08/2024.
 */
class DownloadWorker(context: Context, params: WorkerParameters) :
    CoroutineWorker(context, params) {

    private val notification by lazy { NotificationHandler(applicationContext) }
    private val cacheDataStoreRepository by lazy { CacheDataStoreRepository(applicationContext) }

    override suspend fun doWork(): Result {
        notification.showNotification("Download Worker Start!")
        cacheDataStoreRepository.updateDownloadStatus(CacheDataStoreRepository.DOWNLOAD_INPROGRESS)

        (0..100).forEach {
            cacheDataStoreRepository.updateDownloadProgress(it)
            delay(300)
        }

        notification.showNotification("Download Worker Finished!")
        cacheDataStoreRepository.updateDownloadStatus(CacheDataStoreRepository.DOWNLOAD_SUCCESS)

        return Result.success()
    }
}

