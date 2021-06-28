package com.example.coinstalk.mappers

import com.example.coinstalk.StalkCoin
import com.example.coinstalk.local.StalkCache
import javax.inject.Inject

class StalkMapper @Inject constructor() : Mapper<List<StalkCache>, List<StalkCoin>> {
    override fun mapToFirst(type: List<StalkCoin>): List<StalkCache> {
        return with(type){
            this.map {
                StalkCache(
                    uuid = it.uuid,
                    symbol = it.symbol,
                    name = it.name,
                    color = it.color,
                    iconUrl = it.iconUrl,
                    marketCap = it.marketCap,
                    price = it.price,
                    change = it.change,
                    isFavorite = it.isFavorite
                )
            }
        }
    }

    override fun mapToSecond(type: List<StalkCache>): List<StalkCoin> {
        return with(type){
            this.map {
                StalkCoin(
                    uuid = it.uuid,
                    symbol = it.symbol,
                    name = it.name,
                    color = it.color,
                    iconUrl = it.iconUrl,
                    marketCap = it.marketCap,
                    price = it.price,
                    change = it.change,
                    isFavorite = it.isFavorite
                )
            }
        }
    }

}