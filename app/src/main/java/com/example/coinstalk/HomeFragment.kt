package com.example.coinstalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.coinstalk.databinding.FragmentHomeBinding
import com.example.coinstalk.utils.Result
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: StalkViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRemoteCoins()

        viewModel.coins.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    binding.progress.visibility = View.INVISIBLE
                    binding.homeTv.text = result.data.toString()
                }
                is Result.Error -> {
                    binding.progress.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    binding.progress.visibility = View.VISIBLE
                }
            }


        })
    }


}