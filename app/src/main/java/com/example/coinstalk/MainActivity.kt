package com.example.coinstalk

import android.R
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.coinstalk.databinding.ActivityMainBinding
import com.example.coinstalk.utils.RANDOM_COIN_ID
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.example.coinstalk.worker.StalkWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var navHostFragment: NavHostFragment
    lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var helper: SharedPreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpNavigation()
        setUpWorker()


    }

    private fun setUpNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.navigation,
            navHostFragment.navController
        )
    }

    private fun setUpWorker() {
        if (helper.randomCoin.isNotEmpty()) {
            val data = Data.Builder()
                .putString(RANDOM_COIN_ID, helper.randomCoin)
                .build()
            val notifyRequest =
                PeriodicWorkRequestBuilder<StalkWorker>(2, TimeUnit.MINUTES)
                    .setInputData(data)
                    .build()
            WorkManager
                .getInstance(applicationContext)
                .enqueue(notifyRequest)
        }
    }


}