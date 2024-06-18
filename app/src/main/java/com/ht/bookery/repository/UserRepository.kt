package com.ht.bookery.repository

import com.ht.bookery.services.user.UserApiService
import com.ht.bookery.utils.apiRequestFlow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val service: UserApiService,
) {
    fun getUserInfo() = apiRequestFlow {
        service.getUserInfo()
    }

    fun logout() = apiRequestFlow {
        service.logout()
    }
}