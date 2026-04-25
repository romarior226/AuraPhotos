package com.example.unplashapi.data

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "auth_prefs")

object AuthPreferences {
    val ACCESS_TOKEN = stringPreferencesKey("access_token")
    val USERNAME = stringPreferencesKey("username")
}