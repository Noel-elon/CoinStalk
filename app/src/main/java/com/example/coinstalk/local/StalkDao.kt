package com.example.coinstalk.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StalkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCoins(coins: List<StalkCache>)

    @Query("SELECT * FROM stalk_table")
    fun fetchCoins() : Flow<List<StalkCache>>

    @Query("SELECT * FROM stalk_table WHERE uuid = :id ")
    fun fetchCoinById(id : String) : Flow<StalkCache>

    @Query("SELECT * FROM stalk_table WHERE isFavorite = :fav ")
    fun fetchFavouriteCoins(fav : Boolean) : Flow<List<StalkCache>>

    @Query("UPDATE stalk_table SET isFavorite = :fav WHERE uuid = :id")
    fun updateCoin(id: String, fav: Boolean)
}