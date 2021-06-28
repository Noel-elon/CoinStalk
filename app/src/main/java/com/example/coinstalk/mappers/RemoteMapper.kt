package com.example.coinstalk.mappers

import com.example.coinstalk.local.StalkCache
import com.example.coinstalk.remote.CoinResponse
import javax.inject.Inject

class RemoteMapper @Inject constructor() : Mapper<List<StalkCache>, List<CoinResponse>> {
    override fun mapToFirst(type: List<CoinResponse>): List<StalkCache> {
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
                    change = it.change
                )
            }
        }
    }

    override fun mapToSecond(type: List<StalkCache>): List<CoinResponse> {
        return with(type){
            this.map {
                CoinResponse(
                    uuid = it.uuid,
                    symbol = it.symbol,
                    name = it.name,
                    color = it.color,
                    iconUrl = it.iconUrl,
                    marketCap = it.marketCap,
                    price = it.price,
                    change = it.change
                )
            }
        }
    }


}