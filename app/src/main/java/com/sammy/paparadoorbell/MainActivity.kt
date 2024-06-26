package com.sammy.paparadoorbell

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.sammy.paparadoorbell.ui.theme.PaparaDoorbellTheme
import com.sammy.paparadoorbell.utils.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val workRequest = PeriodicWorkRequestBuilder<RecipeCheckWorker>(5, TimeUnit.MINUTES).build()
        WorkManager.getInstance(this).enqueue(workRequest)
        val startDestination = if (isInternetAvailable(this)) {
            SpoonacularDestination.SPLASH
        } else {
            SpoonacularDestination.FAVORITE_RECIPE
        }
        setContent {
            PaparaDoorbellTheme {
                SpoonacularNavigationGraph(startDestination = startDestination)
            }
        }
    }
}

