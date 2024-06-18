package com.ht.bookery.services.auth

import com.ht.bookery.models.Auth
import com.ht.bookery.models.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/authenticate")
    suspend fun login(@Body auth: Auth): Response<LoginResponse>

//    @POST("auth/logout")
//    suspend fun logout(): Response<Void>

    @POST("auth/refresh-token")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<LoginResponse>
}
