package com.example.challengechapter7.dependency_injection

import android.content.Context
import com.example.challengechapter7.network.ApiGhibliFilmServices
import com.example.challengechapter7.network.ApiUserServices
import com.example.challengechapter7.roomdatabase.FavoriteFilmDao
import com.example.challengechapter7.roomdatabase.FavoriteFilmDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GhibliFilmAppModule {
    //base url for user API
    private const val BASE_URL = "https://6254434c19bc53e2347b93f1.mockapi.io/"

    //base url for ghibli film API
    private const val BASE_URL_2 = "https://ghibliapi.herokuapp.com"


    private val logging: HttpLoggingInterceptor
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
    fun provideRetrofitForUser(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideUserFromApi(retrofit: Retrofit): ApiUserServices =
        retrofit.create(ApiUserServices::class.java)

    //retrofit module for ghibli film API
    @Provides
    @Singleton
    @Named("GHIBLI_FILM_RETROFIT")
    fun provideRetrofitForGhibliFilm(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL_2)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    @Named("GHIBLI_FILM_DATA")
    fun provideGhibliFilmFromApi(@Named("GHIBLI_FILM_RETROFIT") retrofit: Retrofit):
            ApiGhibliFilmServices = retrofit.create(ApiGhibliFilmServices::class.java)

    @Provides
    @Singleton
    fun provideFavoriteGhibliFilmDatabase(@ApplicationContext context: Context): FavoriteFilmDatabase =
        FavoriteFilmDatabase.getInstance(context)!!

    @Provides
    @Singleton
    fun provideFavoriteGhibliFilmDao(favoriteFilmDatabase: FavoriteFilmDatabase): FavoriteFilmDao =
        favoriteFilmDatabase.favoriteFilmDao()

}