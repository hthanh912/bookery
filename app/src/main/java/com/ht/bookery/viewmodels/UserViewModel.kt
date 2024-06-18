package com.ht.bookery.viewmodels

import androidx.lifecycle.MutableLiveData
import com.ht.bookery.models.UserInfoResponse
import com.ht.bookery.repository.UserRepository
import com.ht.bookery.utils.ApiResponse
import com.ht.bookery.utils.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userManager: UserManager
): BaseViewModel() {

    private val _userInfoResponse = MutableLiveData<ApiResponse<UserInfoResponse>>()
    val userInfoResponse = _userInfoResponse

    val err = object : CoroutinesErrorHandler {
        override fun onError(message: String) {

        }
    }
    fun onClickLogout() {
        println("onClickLogout")
        logout(object : CoroutinesErrorHandler {
            override fun onError(message: String) {
                println("Logout failed")
            }

        })
    }

    fun logout(coroutinesErrorHandler: CoroutinesErrorHandler) {
//        _loginResponse.value = null
        CoroutineScope(Dispatchers.IO).launch {
            userManager.deleteSession()
        }
//        return baseRequest(_userLogoutResponse, coroutinesErrorHandler) {
//            userRepository.logout()
//        }
    }

    fun onClickGetUserInfo() = baseRequest(_userInfoResponse, err) {
        userRepository.getUserInfo()
    }

}