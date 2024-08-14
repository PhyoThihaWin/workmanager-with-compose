package com.pthw.workmanagerwithcompose

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pthw.workmanagerwithcompose.composable.permission.RequestNotificationPermissionDialog
import com.pthw.workmanagerwithcompose.ui.theme.WorkManagerWithComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkManagerWithComposeTheme {
                // HomePage
                HomePage()

                // notification permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    RequestNotificationPermissionDialog()
                }
            }
        }
    }

}