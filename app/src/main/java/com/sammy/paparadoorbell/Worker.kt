package com.sammy.paparadoorbell

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class RecipeCheckWorker(appContext: Context, workerParams: WorkerParameters):
    Worker(appContext, workerParams) {

    override fun doWork(): Result {
        Log.d("RecipeCheckWorker", "Merhaba Papara Bu Bir Worker Örneğidir.")
        return Result.success()
    }
}