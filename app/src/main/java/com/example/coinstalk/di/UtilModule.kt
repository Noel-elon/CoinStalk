package com.example.coinstalk.di

import android.content.Context
import android.content.SharedPreferences
import com.example.coinstalk.utils.PREF_NAME
import com.example.coinstalk.utils.SharedPreferenceHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Provides
    @Singleton
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(
            PREF_NAME,
            Context.MODE_PRIVATE
        )
    }

    @Provides
    fun provideSharedPreferenceHelper(sharedPreferences: SharedPreferences): SharedPreferenceHelper {
        return SharedPreferenceHelper(sharedPreferences)
    }
}