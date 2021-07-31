package com.example.coinstalk.utils

import android.content.SharedPreferences
import javax.inject.Inject

 open class SharedPreferenceHelper @Inject constructor(
    private val preferences: SharedPreferences
) {
    var randomCoin: String
        get() = preferences.getString(COIN_NOTIF, "").toString()
        set(value) = preferences.edit().putString(COIN_NOTIF, value).apply()

    var userAlias: String
        get() = preferences.getString(USER_ALIAS_ID, "").toString()
        set(value) = preferences.edit().putString(USER_ALIAS_ID, value).apply()

     var theme: String
         get() = preferences.getString(THEME_KEY, ThemeHelper.DEFAULT_MODE)!!
         set(value) = preferences.edit().putString(THEME_KEY, value).apply()
}