package com.example.coinstalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coinstalk.databinding.FragmentCoinDetailBinding
import com.example.coinstalk.databinding.FragmentHomeBinding

class CoinDetailFragment : Fragment() {

    lateinit var binding : FragmentCoinDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

}