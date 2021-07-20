package com.example.coinstalk.remote

data class BaseResponse(
    val status: String,
    val data: DataResponse
)

data class DataResponse(
    val coins: List<CoinResponse>
)

data class CoinResponse(
    val uuid: String,
    val symbol: String,
    val name: String,
    val color: String? = null,
    val iconUrl: String,
    val marketCap: String,
    val price: String,
    val change: String
)