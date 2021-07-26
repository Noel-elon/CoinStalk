package com.example.coinstalk

import android.app.Application
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.coinstalk.utils.RANDOM_COIN_ID
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.example.coinstalk.worker.StalkWorker
import com.google.gson.Gson
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class StalkApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }



}