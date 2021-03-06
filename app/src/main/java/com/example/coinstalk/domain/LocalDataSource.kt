package com.example.coinstalk.domain

import com.example.coinstalk.StalkCoin
import com.example.coinstalk.local.StalkCache
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun fetchAllCoins() : Flow<List<StalkCoin>>
    fun saveCoins(coins: List<StalkCache>) : Flow<Unit>
    fun fetchCoinById(id : String) : Flow<StalkCoin>
    fun fetchFavouriteCoins() : Flow<List<StalkCoin>>
    fun updateCoin(id: String, fav: Boolean) : Flow<Unit>
}