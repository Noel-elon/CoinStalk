package com.example.coinstalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coinstalk.adapters.CoinsAdapter
import com.example.coinstalk.adapters.GainersAdapter
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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRemoteCoins()
        val coinAdapter = CoinsAdapter {
            val bundle = bundleOf("coin_id" to it)
            findNavController().navigate(R.id.action_homeFragment_to_coinDetailFragment, bundle)
        }

        val gainersAdapter = GainersAdapter {
            val bundle = bundleOf("coin_id" to it)
            findNavController().navigate(R.id.action_homeFragment_to_coinDetailFragment, bundle)
        }

        binding.coinsRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = coinAdapter
        }

        binding.gainersRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            adapter = gainersAdapter
        }

        viewModel.coins.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    coinAdapter.submitList(result.data)
                    gainersAdapter.submitList(result.data)
                }
                is Result.Error -> {

                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }


        })
    }


}