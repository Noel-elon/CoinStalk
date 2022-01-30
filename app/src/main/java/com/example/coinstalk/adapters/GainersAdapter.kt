package com.example.coinstalk.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstalk.R
import com.example.coinstalk.StalkCoin
import com.example.coinstalk.databinding.TopGainersListItemBinding
import com.example.coinstalk.utils.*

class GainersAdapter(
    private val itemClick: (itemId: String) -> Unit
) : ListAdapter<StalkCoin, GainersAdapter.GainersViewHolder>(StalkDiffUtil()) {


    inner class GainersViewHolder(private val binding: TopGainersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: StalkCoin) = with(binding) {
            coinAbbrevTv.text = item.symbol
            coinPriceTv.text = "$${item.price.twoDecimals()}"
            if (gained(item.change)){
                gainTv.text = "+${item.change.twoDecimals()}%"
                    gainTv.setColor(R.color.green)
            }else{
                gainTv.text = "${item.change.twoDecimals()}%"
                gainTv.setColor(R.color.red)

            }
            imageView.loadUrl(item.iconUrl)
            root.setOnClickListener {
                itemClick(item.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GainersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.top_gainers_list_item, parent, false)

        return GainersViewHolder(TopGainersListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: GainersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}