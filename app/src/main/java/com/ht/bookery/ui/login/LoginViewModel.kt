package com.ht.bookery.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ht.bookery.models.Auth
import com.ht.bookery.models.LoginResponse
import com.ht.bookery.repository.AuthRepository
import com.ht.bookery.repository.UserRepository
import com.ht.bookery.utils.ApiResponse
import com.ht.bookery.utils.UserManager
import com.ht.bookery.viewmodels.BaseViewModel
import com.ht.bookery.viewmodels.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository,
    private val userManager: UserManager
    ): BaseViewModel() {

    val tag = "LoginViewModel"

    val username = MutableLiveData("")
    val password = MutableLiveData("")

    private val _loginResponse = MutableLiveData<ApiResponse<LoginResponse>?>()
    val loginResponse = _loginResponse

    fun onClickLogin() {
        if (username.value != null && password.value != null) {
            login(Auth(username.value.toString(), password.value.toString()), object : CoroutinesErrorHandler {
                override fun onError(message: String) {
                    println("$tag: $message")
                }
            })
        }
    }

    fun login(auth: Auth, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(auth)
    }

    fun saveTokens(accessToken: String, refreshToken: String) {
        viewModelScope.launch {
            userManager.saveTokenToDataStore(accessToken, refreshToken)
        }
//        authViewModel.userLogoutResponse.value = null
    }

}


//saveTokens
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNzk0NDU3MywiZXhwIjoxNzE3OTQ0NjczfQ.L5UAKswKg9L6hFVCicLxO8gGJqrjDXRKX8AVm7y2wY0
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNzk0NDU3MywiZXhwIjoxNzE3OTQ0ODczfQ.ZSiisXP_DIjHiw0-H85GRvRFeeS835lWIqXAXrhtw2U

//authViewModel.accessToken
// eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcxNzk0NDU3MywiZXhwIjoxNzE3OTQ0NjczfQ.L5UAKswKg9L6hFVCicLxO8gGJqrjDXRKX8AVm7y2wY0

