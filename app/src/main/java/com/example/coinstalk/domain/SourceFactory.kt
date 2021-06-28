package com.example.coinstalk.domain

import javax.inject.Inject

class SourceFactory @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {
    fun remote() = remoteDataSource
    fun local() = localDataSource
}