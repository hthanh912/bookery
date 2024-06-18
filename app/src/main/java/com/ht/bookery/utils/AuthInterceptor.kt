package com.ht.bookery.utils

import com.ht.bookery.services.auth.AuthApiService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userManager: UserManager,
    private val authApiService: AuthApiService,
) : Interceptor {

    private fun refreshToken(refreshToken: String, chain: Interceptor.Chain, request: Request):Response {

        if (refreshToken != "") {
            val response = runBlocking {
                refreshToken.let { authApiService.refreshToken("Bearer ${refreshToken}") }
            }
            if (response.isSuccessful) {
                response.body()?.let {

                    runBlocking {
                        userManager.saveTokenToDataStore(it.accessToken, it.refreshToken)
                    }

                    val newRequest = request
                        .newBuilder()
                        .header("Authorization", "Bearer ${it.accessToken}")
                        .build()
                    return chain.proceed(newRequest)

                }
            } else {
                runBlocking {
                    userManager.deleteSession()
                }
            }
        }

        return chain.proceed(request)

    }

    override fun intercept(chain: Interceptor.Chain): Response {
//        CoroutineScope(Dispatchers.IO).launch {
//            userManager.getFromDataStore().collect {
//                println("userManager.getFromDataStore()." + it.accessToken)
//                token = it.accessToken
//            }
//        }

        val data = runBlocking { userManager.getFromDataStore().first() }
        val request = chain.request()
        return if (data.accessToken != "") {

            val newRequest = request
                .newBuilder()
                .header("Authorization", "Bearer ${data.accessToken}")
                .build()
            val response = chain.proceed(newRequest)
            if (response.code == 401) {
                response.close()
                refreshToken(data.refreshToken, chain, request)
            } else {
                response
            }
        } else {
            refreshToken(data.refreshToken, chain, request)
        }
    }
}