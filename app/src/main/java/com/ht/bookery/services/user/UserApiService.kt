package com.ht.bookery.services.user

import com.ht.bookery.models.LogoutResponse
import com.ht.bookery.models.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApiService {
    @GET("users/me")
    suspend fun getUserInfo(): Response<UserInfoResponse>

    @POST("auth/logout")
    suspend fun logout(): Response<LogoutResponse>
}