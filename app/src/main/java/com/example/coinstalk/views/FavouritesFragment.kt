package com.example.coinstalk.views

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
import com.example.coinstalk.R
import com.example.coinstalk.StalkCoin
import com.example.coinstalk.viewmodel.StalkViewModel
import com.example.coinstalk.adapters.CoinsAdapter
import com.example.coinstalk.databinding.FragmentFavouritesBinding
import com.example.coinstalk.utils.COIN_ID
import com.example.coinstalk.utils.Result
import com.example.coinstalk.utils.SharedPreferenceHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
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
                    if (result.data?.isNotEmpty() == true) {
                        coinAdapter.submitList(result.data)
                        result.data?.let {
                            binding.noFavesTxt.visibility = View.GONE
                            savedCoins = it
                            val coinString = Gson().toJson(it.random())
                            prefHelper.randomCoin = coinString
                        }
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

                val list = coins.filter {
                    it.isFavorite
                }
                if (!list.isNullOrEmpty()) {
                    viewModel.getFavouriteCoins()
                    binding.noFavesTxt.visibility = View.GONE
                }
            } else {
                viewModel.saveCoins(coins)
                val list = coins.filter {
                    it.isFavorite
                }
                if (!list.isNullOrEmpty()) {
                    viewModel.getFavouriteCoins()
                    binding.noFavesTxt.visibility = View.GONE
                }
            }

        })
    }

    private fun navigateToDetail(id: String) {
        val bundle = bundleOf(COIN_ID to id)
        findNavController().navigate(R.id.action_favouritesFragment_to_coinDetailFragment, bundle)
    }


}