package com.example.coinstalk.mappers

import com.example.coinstalk.StalkCoin
import com.example.coinstalk.local.StalkCache
import javax.inject.Inject

class SingleStalkMapper @Inject constructor() : Mapper<StalkCache, StalkCoin> {
    override fun mapToFirst(type: StalkCoin): StalkCache {
        return with(type) {
            StalkCache(
                uuid, symbol, name, color, iconUrl, marketCap, price, change, isFavorite
            )
        }
    }

    override fun mapToSecond(type: StalkCache): StalkCoin {
        return with(type) {
            StalkCoin(
                uuid, symbol, name, color, iconUrl, marketCap, price, change, isFavorite
            )
        }
    }
}