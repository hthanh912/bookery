package com.ht.bookery.utils

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.ht.bookery.dataStore
import kotlinx.coroutines.flow.map

data class UserPreferences(
    val accessToken: String,
    val refreshToken: String,
    val firstName: String)

class UserManager(val context: Context) {

    companion object {
        val ACCESS_TOKEN = stringPreferencesKey("access_token")
        val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
        val FIRST_NAME = stringPreferencesKey("first_name")
        val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    }

    fun getFromDataStore() = context.dataStore.data.map {
        UserPreferences(
            it[ACCESS_TOKEN] ?: "",
            it[REFRESH_TOKEN] ?: "",
            it[FIRST_NAME] ?: ""
        )
    }

    suspend fun saveTokenToDataStore(accessToken: String?, refreshToken: String?) {
        context.dataStore.edit {
            it[ACCESS_TOKEN] = accessToken ?: ""
            it[REFRESH_TOKEN] = refreshToken ?: ""
        }
    }

//    suspend fun saveUserInfoToDataStore(firstName: String?) {
//        context.dataStore.edit {
//            it[FIRST_NAME] = firstName ?: ""
//        }
//    }

    suspend fun deleteSession() = context.dataStore.edit {
        it.clear()
    }
}
