package com.example.coinstalk.views

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.example.coinstalk.R
import com.example.coinstalk.StalkCoin
import com.example.coinstalk.viewmodel.StalkViewModel
import com.example.coinstalk.databinding.FragmentCoinDetailBinding
import com.example.coinstalk.utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    private lateinit var binding: FragmentCoinDetailBinding
    private lateinit var currentCoin: StalkCoin
    private val coinId by lazy { arguments?.getString(COIN_ID) }
    private val viewModel: StalkViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCoinDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.findViewById<BottomNavigationView>(R.id.navigation)?.visibility = View.GONE
        coinId?.let {
            viewModel.getSingleCoin(it)
        }

        binding.favButton.setOnClickListener {
            if (!currentCoin.isFavorite) {
                binding.favButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_favorite_border
                    )
                )
                viewModel.toggleFavouriteCoin(currentCoin.uuid, true)
            } else {
                binding.favButton.setImageDrawable(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_favorite
                    )
                )
                viewModel.toggleFavouriteCoin(currentCoin.uuid, false)
            }
        }


        viewModel.singleCoin.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { coin ->
                        populateViews(coin)
                        currentCoin = coin
                        if (!coin.isFavorite) {
                            binding.favButton.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_favorite_border
                                )
                            )
                        } else {
                            binding.favButton.setImageDrawable(
                                ContextCompat.getDrawable(
                                    requireContext(),
                                    R.drawable.ic_baseline_favorite
                                )
                            )
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

        viewModel.favResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Result.Success -> {
                    response.data?.let { msg ->
                        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                is Result.Error -> {

                    Toast.makeText(requireContext(), response.errorMessage, Toast.LENGTH_SHORT)
                        .show()
                }
                else -> {

                }
            }

        })
    }


    private fun populateViews(coin: StalkCoin) {
        with(binding) {
            try {
                if (coin.color.isNotEmpty()) {
                    detailConstraintLayout.setBackgroundColor(Color.parseColor(coin.color))
                }

            } catch (e: IllegalArgumentException) {
                Timber.e(e)
            }

            detailIv.loadUrl(coin.iconUrl)
            detailNameTv.text = coin.name
            if (gained(coin.change)) {
                detailChangeTv.text = "+${coin.change.twoDecimals()}%"
                detailChangeTv.setColor(R.color.green)
            } else {
                detailChangeTv.text = coin.change.twoDecimals()
                detailChangeTv.setColor(R.color.red)
            }
            symbolTv.text = coin.symbol
            marketCapTv.text = coin.marketCap.twoDecimals()
            detailPriceTv.text = coin.price.twoDecimals()

        }
    }

}