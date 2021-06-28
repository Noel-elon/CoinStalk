package com.example.coinstalk.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stalk_table")
data class StalkCache(
    @PrimaryKey
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String,
    val iconUrl: String,
    val marketCap: String,
    val price: String,
    val change: String,
    var isFavorite : Boolean = false
)