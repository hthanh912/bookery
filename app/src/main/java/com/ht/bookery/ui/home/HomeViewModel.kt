package com.ht.bookery.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ht.bookery.models.UserInfoResponse
import com.ht.bookery.repository.UserRepository
import com.ht.bookery.utils.ApiResponse
import com.ht.bookery.utils.UserManager
import com.ht.bookery.viewmodels.BaseViewModel
import com.ht.bookery.viewmodels.CoroutinesErrorHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val userManager: UserManager
): BaseViewModel() {

    private val _userInfoResponse = MutableLiveData<ApiResponse<UserInfoResponse?>>()
    val userInfoResponse = _userInfoResponse

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            userManager.getFromDataStore().collect {
                if (it.accessToken.isNotEmpty()) {
                    baseRequest(_userInfoResponse, object : CoroutinesErrorHandler {
                        override fun onError(message: String) {
                            println("Fail to get user info")
                        }
                    }) {
                        userRepository.getUserInfo()
                    }
                }
            }

        }
    }

}