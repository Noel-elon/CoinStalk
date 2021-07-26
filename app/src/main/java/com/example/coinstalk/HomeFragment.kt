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
import com.example.coinstalk.utils.COIN_ID
import com.example.coinstalk.utils.Result
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: StalkViewModel by viewModels()
    private lateinit var savedCoins: List<StalkCoin>

    @Inject
    lateinit var helper: SharedPreferenceHelper
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.findViewById<BottomNavigationView>(R.id.navigation)?.visibility = View.VISIBLE
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRemoteCoins()

        binding.homeNameTv.text = "Hey, ${helper.userAlias}"
        val coinAdapter = CoinsAdapter {
            navigateToDetail(it)
        }

        val gainersAdapter = GainersAdapter {
            navigateToDetail(it)
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
                    result.data?.let {
                        savedCoins = it
                    }

                }
                is Result.Error -> {

                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
                }
                else -> {

                }
            }


        })

        viewModel.remoteCoins.observe(viewLifecycleOwner, { coins ->
            if (savedCoins.isNotEmpty()) {
                coins.forEach {
                    val saved = savedCoins.find { s ->
                        s.uuid == it.uuid
                    }
                    if (saved != null && saved.isFavorite) {
                        coins.find { c ->
                            c.uuid == it.uuid
                        }?.isFavorite = true
                    }
                }
                viewModel.saveCoins(coins)
            } else {
                viewModel.saveCoins(coins)
            }

        })
    }

    private fun navigateToDetail(id: String) {
        val bundle = bundleOf(COIN_ID to id)
        findNavController().navigate(R.id.action_homeFragment_to_coinDetailFragment, bundle)
    }


}