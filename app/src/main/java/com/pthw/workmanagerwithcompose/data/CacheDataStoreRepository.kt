package com.pthw.workmanagerwithcompose.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

/**
 * Created by P.T.H.W on 13/08/2024.
 */
class CacheDataStoreRepository(private val context: Context) {
    companion object {
        private val Context.dataStore by preferencesDataStore("pref.foodDi")

        private val PREF_KEY_DOWNLOAD_STATUS = stringPreferencesKey("download_status.key")
        private val PREF_KEY_DOWNLOAD_PROGRESS = intPreferencesKey("download_progress.key")
        private val PREF_KEY_PERIODIC_TIME = stringPreferencesKey("download_period_time.key")

        const val DOWNLOAD_SUCCESS = "Download Success"
        const val DOWNLOAD_IDLE = "Download Idle"
        const val DOWNLOAD_FAILED = "Download Failed"
        const val DOWNLOAD_INPROGRESS = "Downloading.."

    }

    fun getDownloadStatus() = context.dataStore.data.map {
        it[PREF_KEY_DOWNLOAD_STATUS] ?: DOWNLOAD_IDLE
    }

    suspend fun updateDownloadStatus(status: String) {
        context.dataStore.edit {
            it[PREF_KEY_DOWNLOAD_STATUS] = status
        }
    }

    fun getDownloadProgress() = context.dataStore.data.map {
        it[PREF_KEY_DOWNLOAD_PROGRESS] ?: 0
    }

    suspend fun updateDownloadProgress(progress: Int) {
        context.dataStore.edit {
            it[PREF_KEY_DOWNLOAD_PROGRESS] = progress
        }
    }

    fun getPeriodicTime() = context.dataStore.data.map {
        it[PREF_KEY_PERIODIC_TIME] ?: "No Data"
    }

    suspend fun updatePeriodicTime(time: String) {
        context.dataStore.edit {
            it[PREF_KEY_PERIODIC_TIME] = time
        }
    }

}