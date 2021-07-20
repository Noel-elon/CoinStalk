package com.example.coinstalk.usecases

import com.example.coinstalk.domain.LocalDataSource
import com.example.coinstalk.local.StalkCache
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveCoinsUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource
) {
    fun execute(coins: List<StalkCache>) =
        localDataSource.saveCoins(coins).flowOn(coroutineDispatcher)
}