package com.example.unplashapi

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.example.unplashapi.data.AuthPreferences
import com.example.unplashapi.data.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppDataStore(private val context: Context) {

    val accessToken: Flow<String?> = context.dataStore.data
        .map { it[AuthPreferences.ACCESS_TOKEN] }

    val userName: Flow<String?> = context.dataStore.data.map {
        it[AuthPreferences.USERNAME]
    }

    suspend fun saveUsername(userName: String) {
        context.dataStore.edit { prefs ->
            prefs[AuthPreferences.USERNAME] = userName
        }
    }

    suspend fun updateUserName(newUserName: String) {
        context.dataStore.edit { preferences ->
            preferences[AuthPreferences.USERNAME] = newUserName
        }
    }

    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[AuthPreferences.ACCESS_TOKEN] = token
        }
    }


    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(AuthPreferences.ACCESS_TOKEN)
        }
    }
}