package com.example.coinstalk.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.coinstalk.databinding.ActivityOnboardingBinding
import com.example.coinstalk.utils.SharedPreferenceHelper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOnboardingBinding

    @Inject
    lateinit var helper: SharedPreferenceHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)


        if (!helper.userAlias.isNullOrEmpty()) {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
        }

        binding.nextButton.setOnClickListener {
            if (binding.yourNameEt.text?.isNotEmpty() == true) {
                helper.userAlias = binding.yourNameEt.text.toString()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
            } else {
                Toast.makeText(this, "Fill the box please", Toast.LENGTH_SHORT).show()
            }

        }

    }
}