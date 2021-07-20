package com.example.coinstalk.repository

import com.example.coinstalk.StalkCoin
import com.example.coinstalk.local.StalkCache
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun fetchAllCoins(coins : List<StalkCache>) : Flow<List<StalkCoin>>
    fun saveCoins(coins: List<StalkCoin>) : Flow<Unit>
    fun fetchCoinById(id : String) : Flow<StalkCoin>
    fun fetchFavouriteCoins(fav : Boolean) : Flow<List<StalkCoin>>
    fun updateCoin(id: String, fav: Boolean) : Flow<Unit>
}