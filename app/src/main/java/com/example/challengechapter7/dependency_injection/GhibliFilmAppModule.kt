package com.example.challengechapter7.dependency_injection

import com.example.challengechapter7.network.ApiUserServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GhibliFilmAppModule {
    private const val BASE_URL = "https://6254434c19bc53e2347b93f1.mockapi.io/"
    private val logging : HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }
    private val client = OkHttpClient.Builder().addInterceptor(logging).build()

    //retrofit module for user API
    @Provides
    @Singleton
    fun provideRetrofitForUser() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideUserFromApi(retrofit: Retrofit) : ApiUserServices = retrofit.create(ApiUserServices::class.java)

    //retrofit module for ghibli film API


}