package com.sammy.paparadoorbell

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sammy.paparadoorbell.ui.theme.PaparaDoorbellTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val workRequest = PeriodicWorkRequestBuilder<RecipeCheckWorker>(20, TimeUnit.SECONDS).build()
        WorkManager.getInstance(this).enqueue(workRequest)
        setContent {
            PaparaDoorbellTheme {
                SpoonacularNavigationGraph()
            }
        }
    }
}

