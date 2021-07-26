package com.example.coinstalk.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.coinstalk.StalkCoin
import com.example.coinstalk.utils.RANDOM_COIN_ID
import com.example.coinstalk.utils.sendNotification
import com.google.gson.Gson

class StalkWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {
    override fun doWork(): Result {
        val coinString = inputData.getString(RANDOM_COIN_ID)
        val coin = Gson().fromJson(coinString, StalkCoin::class.java)
        sendNotification(context = this.applicationContext, coin, "Noel")
        return Result.success()
    }
}