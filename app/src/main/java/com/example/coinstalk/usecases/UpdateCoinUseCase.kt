package com.example.coinstalk.usecases

import com.example.coinstalk.domain.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UpdateCoinUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource
) {
    fun execute(id: String, fav: Boolean) =
        localDataSource.updateCoin(id, fav).flowOn(coroutineDispatcher)
}