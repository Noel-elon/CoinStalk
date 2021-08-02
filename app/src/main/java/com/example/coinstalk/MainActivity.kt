package com.example.coinstalk

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.coinstalk.databinding.ActivityMainBinding
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.example.coinstalk.utils.ThemeHelper
import dagger.hilt.android.AndroidEntryPoint
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

        if (helper.theme.isNotEmpty()) {
            ThemeHelper.applyTheme(helper.theme)
        }
        setContentView(binding.root)
        setUpNavigation()


    }

    private fun setUpNavigation() {
        navHostFragment =
            supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        NavigationUI.setupWithNavController(
            binding.navigation,
            navHostFragment.navController
        )
    }


}