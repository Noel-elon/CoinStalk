package com.example.coinstalk


data class StalkCoin(
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String,
    val iconUrl: String,
    val marketCap: String,
    val price: String,
    val change: String,
    var isFavorite: Boolean
)