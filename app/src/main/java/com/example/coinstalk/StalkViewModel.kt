package com.example.coinstalk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coinstalk.local.StalkCache
import com.example.coinstalk.mappers.RemoteMapper
import com.example.coinstalk.mappers.SingleStalkMapper
import com.example.coinstalk.mappers.StalkMapper
import com.example.coinstalk.usecases.*
import com.example.coinstalk.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StalkViewModel @Inject constructor(
    private val dispatcher: CoroutineDispatcher,
    private val remoteMapper: RemoteMapper,
    private val singleMapper: SingleStalkMapper,
    private val stalkMapper: StalkMapper,
    private val getCoinsRemote: FetchCoinsRemoteUseCase,
    private val getCoinsLocal: GetAllCoinsUseCase,
    private val getCoinById: FetchCoinByIdUseCase,
    private val saveCoins: SaveCoinsUseCase,
    private val updateCoin: UpdateCoinUseCase,
    private val fetchFavCoins: FetchFavCoinsUseCase
) : ViewModel() {

    private val _coins = MutableLiveData<Result<List<StalkCoin>>>()
    val coins = _coins as LiveData<Result<List<StalkCoin>>>

    init {
        getLocalCoins()
    }

    fun getRemoteCoins() {
        viewModelScope.launch(dispatcher) {
            getCoinsRemote.execute().onStart {
                _coins.postValue(Result.Loading)
            }
                .catch {
                    _coins.postValue(Result.Error(it))
                }
                .collect { coinCache ->
                    saveCoins(coinCache)
                }
        }
    }

    private fun getLocalCoins() {
        viewModelScope.launch(dispatcher) {
            getCoinsLocal.execute().onStart {
                _coins.postValue(Result.Loading)
            }
                .catch {
                    _coins.postValue(Result.Error(it))
                }
                .collect { stalkCoin ->
                    _coins.postValue(Result.Success(stalkCoin))
                }
        }
    }

    private fun saveCoins(coins: List<StalkCache>) {
        viewModelScope.launch(dispatcher) {
            saveCoins.execute(coins).collect { }
        }
    }

}