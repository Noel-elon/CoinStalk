package com.example.coinstalk

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.coinstalk.adapters.CoinsAdapter
import com.example.coinstalk.databinding.FragmentCoinDetailBinding
import com.example.coinstalk.databinding.FragmentFavouritesBinding
import com.example.coinstalk.utils.COIN_ID
import com.example.coinstalk.utils.RANDOM_COIN_ID
import com.example.coinstalk.utils.Result
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.example.coinstalk.worker.StalkWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    lateinit var binding: FragmentFavouritesBinding
    private val viewModel: StalkViewModel by viewModels()
    private var savedCoins: List<StalkCoin>? = null

    @Inject
    lateinit var prefHelper: SharedPreferenceHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel.getFavouriteCoins()
        activity?.findViewById<BottomNavigationView>(R.id.navigation)?.visibility = View.VISIBLE
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getRemoteCoins()
        val coinAdapter = CoinsAdapter {
            navigateToDetail(it)
        }

        binding.favesRv.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = coinAdapter
        }

        viewModel.favCoins.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    coinAdapter.submitList(result.data)
                    result.data?.let {
                        savedCoins = it
                        val coinString = Gson().toJson(it.random())
                        prefHelper.randomCoin = coinString
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
            if (savedCoins?.isNotEmpty() == true) {
                coins.forEach {
                    val saved = savedCoins?.find { s ->
                        s.uuid == it.uuid
                    }
                    if (saved != null && saved.isFavorite) {
                        coins.find { c ->
                            c.uuid == it.uuid
                        }?.isFavorite = true
                    }
                }
                viewModel.saveCoins(coins)
                coinAdapter.submitList(coins.filter {
                    it.isFavorite
                })

            } else {
                viewModel.saveCoins(coins)
                coinAdapter.submitList(coins.filter {
                    it.isFavorite
                })
            }

        })
    }

    private fun navigateToDetail(id: String) {
        val bundle = bundleOf(COIN_ID to id)
        findNavController().navigate(R.id.action_favouritesFragment_to_coinDetailFragment, bundle)
    }


}