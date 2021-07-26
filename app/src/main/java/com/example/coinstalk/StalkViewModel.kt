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
    private val getCoinsRemote: FetchCoinsRemoteUseCase,
    private val getCoinsLocal: GetAllCoinsUseCase,
    private val getCoinById: FetchCoinByIdUseCase,
    private val saveCoins: SaveCoinsUseCase,
    private val updateCoin: UpdateCoinUseCase,
    private val fetchFavCoins: FetchFavCoinsUseCase
) : ViewModel() {

    private val _coins = MutableLiveData<Result<List<StalkCoin>>>()
    val coins = _coins as LiveData<Result<List<StalkCoin>>>

    private val _favResponse = MutableLiveData<Result<String>>()
    val favResponse = _favResponse as LiveData<Result<String>>

    private val _singleCoin = MutableLiveData<Result<StalkCoin>>()
    val singleCoin = _singleCoin as LiveData<Result<StalkCoin>>

    private val _favCoins = MutableLiveData<Result<List<StalkCoin>>>()
    val favCoins = _favCoins as LiveData<Result<List<StalkCoin>>>

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
                   // saveCoins(coinCache)
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

     fun getFavouriteCoins() {
        viewModelScope.launch(dispatcher) {
            fetchFavCoins.execute().onStart {
                _favCoins.postValue(Result.Loading)
            }
                .catch {
                    _favCoins.postValue(Result.Error(it))
                }
                .collect { stalkCoin ->
                    _favCoins.postValue(Result.Success(stalkCoin))
                }
        }
    }

     fun getSingleCoin(id : String) {
        viewModelScope.launch(dispatcher) {
            getCoinById.execute(id).onStart {
                _singleCoin.postValue(Result.Loading)
            }
                .catch {
                    _singleCoin.postValue(Result.Error(it))
                }
                .collect { stalkCoin ->
                    _singleCoin.postValue(Result.Success(stalkCoin))
                }
        }
    }

    fun toggleFavouriteCoin(id : String, fav : Boolean) {
        viewModelScope.launch(dispatcher) {
            updateCoin.execute(id, fav).onStart {
                _favResponse.postValue(Result.Loading)
            }
                .catch {
                    _favResponse.postValue(Result.Error(it))
                }
                .collect {
                    if (fav){
                        _favResponse.postValue(Result.Success("Coin Added to favourites!"))
                    }else{
                        _favResponse.postValue(Result.Success("Coin removed from favourites!"))
                    }

                }
        }
    }

    private fun saveCoins(coins: List<StalkCache>) {
        viewModelScope.launch(dispatcher) {
            saveCoins.execute(coins).collect { }
        }
    }

}