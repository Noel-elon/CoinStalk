package com.example.coinstalk.di

import com.example.coinstalk.domain.LocalDataSource
import com.example.coinstalk.domain.RemoteDataSource
import com.example.coinstalk.local.LocalSourceImpl
import com.example.coinstalk.remote.RemoteSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @Singleton
    abstract fun cacheDataSource(cacheDataSourceImpl: LocalSourceImpl): LocalDataSource

    @Binds
    @Singleton
    abstract fun remoteDataSource(remoteDataSourceImpl: RemoteSourceImpl): RemoteDataSource
}