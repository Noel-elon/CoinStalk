package com.example.coinstalk.usecases

import com.example.coinstalk.domain.LocalDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCoinByIdUseCase @Inject constructor(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val localDataSource: LocalDataSource
) {
    fun execute(id: String) = localDataSource.fetchCoinById(id).flowOn(coroutineDispatcher)
}