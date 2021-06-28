package com.example.coinstalk.domain

import com.example.coinstalk.local.StalkCache
import com.example.coinstalk.remote.BaseResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    fun getCoins(): Flow<List<StalkCache>>
}