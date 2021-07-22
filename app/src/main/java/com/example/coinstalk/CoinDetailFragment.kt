package com.example.coinstalk

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.coinstalk.databinding.FragmentCoinDetailBinding
import com.example.coinstalk.databinding.FragmentHomeBinding
import com.example.coinstalk.utils.*
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.lang.IllegalArgumentException

@AndroidEntryPoint
class CoinDetailFragment : Fragment() {

    lateinit var binding: FragmentCoinDetailBinding
    private val coinId by lazy { arguments?.getString("coin_id") }
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

        coinId?.let {
            viewModel.getSingleCoin(it)
        }

        viewModel.singleCoin.observe(viewLifecycleOwner, { result ->
            when (result) {
                is Result.Success -> {
                    result.data?.let { coin ->
                        populateViews(coin)
                    }
                }
                is Result.Error -> {

                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_SHORT).show()
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