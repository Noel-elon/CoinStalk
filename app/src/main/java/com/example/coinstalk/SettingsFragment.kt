package com.example.coinstalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coinstalk.databinding.FragmentSettingsBinding
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.example.coinstalk.utils.ThemeHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var helper: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        activity?.findViewById<BottomNavigationView>(R.id.navigation)?.visibility = View.VISIBLE
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (helper.theme == ThemeHelper.DARK_MODE) {
            binding.darkModeSwitch.isChecked = true
        }
        binding.darkModeSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                helper.theme = ThemeHelper.DARK_MODE
                ThemeHelper.applyTheme(ThemeHelper.DARK_MODE)
            } else {
                helper.theme = ThemeHelper.LIGHT_MODE
                ThemeHelper.applyTheme(ThemeHelper.LIGHT_MODE)
            }
        }
    }

}