package com.example.coinstalk.local

import com.example.coinstalk.StalkCoin
import com.example.coinstalk.domain.LocalDataSource
import com.example.coinstalk.mappers.SingleStalkMapper
import com.example.coinstalk.mappers.StalkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalSourceImpl @Inject constructor(
    private val database: StalkDatabase,
    private val mapper: StalkMapper,
    private val singleMapper: SingleStalkMapper

) : LocalDataSource {

    override fun fetchAllCoins(): Flow<List<StalkCoin>> {
        return database.stalkDao().fetchCoins().map { data ->
            mapper.mapToSecond(data)
        }
    }

    override fun saveCoins(coins: List<StalkCache>): Flow<Unit> {
        return flow {
            emit(
                database.stalkDao().saveCoins(coins)
            )
        }
    }

    override fun fetchCoinById(id: String): Flow<StalkCoin> {
        return database.stalkDao().fetchCoinById(id).map {
            singleMapper.mapToSecond(it)
        }


    }

    override fun fetchFavouriteCoins(fav: Boolean): Flow<List<StalkCoin>> {
        return database.stalkDao().fetchFavouriteCoins(fav).map {
            mapper.mapToSecond(it)
        }
    }

    override fun updateCoin(id: String, fav: Boolean): Flow<Unit> {
        return flow {
            emit(
                database.stalkDao().updateCoin(id, fav)
            )
        }

    }
}
