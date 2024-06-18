package com.ht.bookery.repository

import com.ht.bookery.models.Auth
import com.ht.bookery.models.LoginResponse
import com.ht.bookery.services.auth.AuthApiService
import com.ht.bookery.utils.apiRequestFlow
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
) {
    fun login(auth: Auth) = apiRequestFlow {
        authApiService.login(auth)
    }

}
