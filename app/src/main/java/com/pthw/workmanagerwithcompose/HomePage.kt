package com.pthw.workmanagerwithcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.pthw.workmanagerwithcompose.data.CacheDataStoreRepository
import com.pthw.workmanagerwithcompose.ui.theme.WorkManagerWithComposeTheme
import com.pthw.workmanagerwithcompose.workers.WorkerManager

/**
 * Created by P.T.H.W on 13/08/2024.
 */

@Composable
fun HomePage() {
    val context = LocalContext.current
    val workerManager by lazy { WorkerManager(context) }
    val cacheDataStoreRepository by lazy { CacheDataStoreRepository(context) }
    val viewModel = HomePageViewModel(cacheDataStoreRepository)

    val currentProgress = viewModel.downloadProgress.collectAsState(initial = 0).value

    Scaffold(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // One time worker request
            Text(text = "OneTimeWorkRequest", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Status: ${viewModel.downloadStatus.collectAsState(initial = CacheDataStoreRepository.DOWNLOAD_IDLE).value}"
            )
            Spacer(modifier = Modifier.height(8.dp))
            LinearProgressIndicator(
                progress = { currentProgress.toFloat() / 100 },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                workerManager.startDownloadWorker()
            }) {
                Text(text = "Start Download")
            }


            Spacer(modifier = Modifier.height(100.dp))


            // Periodic worker request
            Text(text = "PeriodicWorkerRequest", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(30.dp))
            Text(text = "Status: ${viewModel.periodicTime.collectAsState(initial = "No Data").value}")
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                workerManager.startPeriodicWorker()
            }) {
                Text(text = "Start Periodic Worker")
            }


            // Chain one time worker request
            Spacer(modifier = Modifier.height(100.dp))
            Text(text = "ChainWorkersRequest", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = {
                workerManager.startChainDownloadWorkers()
            }) {
                Text(text = "Start Chain Workers")
            }
        }
    }

}

@Preview
@Composable
private fun HomePagePreview() {
    WorkManagerWithComposeTheme {
        HomePage()
    }
}

class HomePageViewModel(
    cacheDataStoreRepository: CacheDataStoreRepository
) : ViewModel() {
    val downloadStatus = cacheDataStoreRepository.getDownloadStatus()
    val downloadProgress = cacheDataStoreRepository.getDownloadProgress()
    val periodicTime = cacheDataStoreRepository.getPeriodicTime()
}