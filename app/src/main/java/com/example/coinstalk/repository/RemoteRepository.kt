package com.example.coinstalk.repository

import com.example.coinstalk.local.StalkCache
import kotlinx.coroutines.flow.Flow

interface RemoteRepository {
    fun getCoins(): Flow<List<StalkCache>>
}