package com.example.coinstalk.remote

import retrofit2.http.GET

interface StalkApi {
    @GET("coins")
   suspend fun getCoins() : BaseResponse
}