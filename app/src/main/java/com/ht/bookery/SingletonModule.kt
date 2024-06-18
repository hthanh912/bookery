package com.ht.bookery

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.ht.bookery.repository.AuthRepository
import com.ht.bookery.repository.UserRepository
import com.ht.bookery.services.auth.AuthApiService
import com.ht.bookery.services.user.UserApiService
import com.ht.bookery.utils.AuthAuthenticator
import com.ht.bookery.utils.AuthInterceptor
import com.ht.bookery.utils.UserManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "data_store")

@Module
@InstallIn(SingletonComponent::class)
class SingletonModule {

    @Singleton
    @Provides
    fun provideUserManager(@ApplicationContext context: Context): UserManager = UserManager(context)



    @Singleton
    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor,
        authAuthenticator: AuthAuthenticator
    ): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(authAuthenticator)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(userManager: UserManager, authApiService: AuthApiService): AuthInterceptor =
        AuthInterceptor(userManager, authApiService)

    @Singleton
    @Provides
    fun provideAuthAuthenticator(userManager: UserManager): AuthAuthenticator =
        AuthAuthenticator(userManager)

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder =
        Retrofit.Builder()
//            .baseUrl("https://wide-barracuda-hthanh912-c60d2cda.koyeb.app")
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(GsonConverterFactory.create())

    @Singleton
    @Provides
    fun provideAuthAPIService(retrofit: Retrofit.Builder): AuthApiService =
        retrofit
            .build()
            .create(AuthApiService::class.java)

    @Singleton
    @Provides
    fun provideUserAPIService(okHttpClient: OkHttpClient, retrofit: Retrofit.Builder): UserApiService =
        retrofit
            .client(okHttpClient)
            .build()
            .create(UserApiService::class.java)

}