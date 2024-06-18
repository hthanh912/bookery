package com.ht.bookery

import com.ht.bookery.repository.AuthRepository
import com.ht.bookery.repository.UserRepository
import com.ht.bookery.services.auth.AuthApiService
import com.ht.bookery.services.user.UserApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {
    @Provides
    fun provideAuthRepository(authApiService: AuthApiService) = AuthRepository(authApiService)

    @Provides
    fun provideUserRepository(userApiService: UserApiService) = UserRepository(userApiService)
}
