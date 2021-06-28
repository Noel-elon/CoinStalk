package com.example.coinstalk.remote

import com.example.coinstalk.domain.RemoteDataSource
import com.example.coinstalk.local.StalkCache
import com.example.coinstalk.mappers.RemoteMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteSourceImpl @Inject constructor(
    private val api: StalkApi,
    private val mapper: RemoteMapper
) : RemoteDataSource {
    override fun getCoins(): Flow<List<StalkCache>> {
        return flow {
            emit(mapper.mapToFirst(api.getCoins().data.coins))
        }
    }
}