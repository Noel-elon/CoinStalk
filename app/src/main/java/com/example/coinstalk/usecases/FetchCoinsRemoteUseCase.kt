package com.example.coinstalk.usecases

import com.example.coinstalk.domain.RemoteDataSource
import com.example.coinstalk.local.StalkCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCoinsRemoteUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val remoteDataSource: RemoteDataSource
) {

    fun execute() : Flow<List<StalkCache>> {
        return remoteDataSource.getCoins().flowOn(coroutineDispatcher)
    }
}