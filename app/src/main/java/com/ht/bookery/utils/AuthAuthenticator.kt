package com.ht.bookery.utils

import com.ht.bookery.models.LoginResponse
import com.ht.bookery.services.auth.AuthApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    val userManager: UserManager
): Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {

        println("******* authenticate *********")

        val refreshToken = runBlocking {
            userManager.getFromDataStore().first().refreshToken
        }

        val newToken = runBlocking { getNewToken(refreshToken) }

        if (!newToken.isSuccessful || newToken.body() == null) { //Couldn't refresh the token, so restart the login process
            runBlocking { userManager.deleteSession() }
        }

        runBlocking {
            userManager.saveTokenToDataStore(newToken.body()?.accessToken, newToken.body()?.refreshToken)
        }

        return response.request.newBuilder()
                .header("Authorization", "Bearer ${newToken.body()?.accessToken}")
                .build()
    }

    private suspend fun getNewToken(refreshToken: String?): retrofit2.Response<LoginResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit.Builder()
//            .baseUrl("https://wide-barracuda-hthanh912-c60d2cda.koyeb.app")
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApiService::class.java)
        return service.refreshToken("Bearer $refreshToken")
    }
}