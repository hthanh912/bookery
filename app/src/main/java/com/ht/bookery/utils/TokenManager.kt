package com.ht.bookery.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.ht.bookery.models.LoginResponse
import com.ht.bookery.services.auth.AuthApiService

//class TokenManager(private val context: Context) {
//
//    private val PREFS_NAME = "sharedpref"
//    val sharedPref: SharedPreferences =
//        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
//
////    fun saveIsLoggedIn(isLoggedIn: Boolean) {
////        sharedPref.edit().putBoolean("is_logged_in", isLoggedIn).commit()
////    }
//
//    fun getAccessToken(): String? {
//        return sharedPref.getString("access_token", null)
//    }
//
//    fun getRefreshToken(): String? {
//        return sharedPref.getString("refresh_token", null)
//    }
//
//    fun saveTokens(accessToken: String, refreshToken: String) {
//        val editor = sharedPref.edit()
//        editor.putString("access_token", accessToken)
//        editor.putString("refresh_token", refreshToken)
//        editor.apply()
//    }
//
//    fun deleteTokens() {
//        val editor = sharedPref.edit()
//        editor.remove("access_token")
//        editor.remove("refresh_token")
//        editor.apply()
//    }
//
//}