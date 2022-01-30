package com.example.coinstalk.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.coinstalk.R
import com.example.coinstalk.StalkCoin
import com.example.coinstalk.databinding.CoinsListItemBinding
import com.example.coinstalk.utils.*

class CoinsAdapter(
    private val itemClick: (id: String) -> Unit
) : ListAdapter<StalkCoin, CoinsAdapter.CoinViewHolder>(StalkDiffUtil()) {


    inner class CoinViewHolder(private val binding: CoinsListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: StalkCoin) = with(binding) {
            coinNameTv.text = item.name
            coinAbbrevTv.text = item.symbol
            coinPriceTv.text = "$${item.price.twoDecimals()}"
            if (gained(item.change)) {
                gainTv.text = "+${item.change.twoDecimals()}%"
                gainTv.setColor(R.color.green)
            } else {
                gainTv.text = "${item.change.twoDecimals()}%"
                gainTv.setColor(R.color.red)
            }
            imageView.loadUrl(item.iconUrl)
            root.setOnClickListener {
                itemClick(item.uuid)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.coins_list_item, parent, false)
        return CoinViewHolder(CoinsListItemBinding.bind(view))
    }

    override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class StalkDiffUtil : DiffUtil.ItemCallback<StalkCoin>() {
    override fun areItemsTheSame(oldItem: StalkCoin, newItem: StalkCoin): Boolean {
        return oldItem.uuid == newItem.uuid
    }

    override fun areContentsTheSame(oldItem: StalkCoin, newItem: StalkCoin): Boolean {
        return oldItem == newItem
    }

}