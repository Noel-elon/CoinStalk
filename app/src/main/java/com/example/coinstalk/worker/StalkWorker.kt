package com.example.coinstalk.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.coinstalk.StalkCoin
import com.example.coinstalk.utils.PREF_NAME
import com.example.coinstalk.utils.RANDOM_COIN_ID
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.example.coinstalk.utils.sendNotification
import com.google.gson.Gson

class StalkWorker(context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    lateinit var helper: SharedPreferenceHelper
    override fun doWork(): Result {
        helper = SharedPreferenceHelper(
            applicationContext.getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
        )
        val coinString = inputData.getString(RANDOM_COIN_ID)
        val coin = Gson().fromJson(coinString, StalkCoin::class.java)
        sendNotification(context = this.applicationContext, coin, helper.userAlias)
        return Result.success()
    }
}