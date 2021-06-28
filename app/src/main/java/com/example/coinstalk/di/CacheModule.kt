package com.example.coinstalk.di

import android.content.Context
import androidx.room.Room
import com.example.coinstalk.local.StalkDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CacheModule {

    @Provides
    @Singleton
    fun dataBase(@ApplicationContext context: Context): StalkDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            StalkDatabase::class.java,
            "stalk_database"
        ).build()
    }

    @Provides
    fun provideCoroutineDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

}