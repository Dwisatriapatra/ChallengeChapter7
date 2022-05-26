package com.example.challengechapter7.network

import com.example.challengechapter7.model.GetAllGhibliFilmResponseItem
import retrofit2.http.GET

interface ApiGhibliFilmServices {
    @GET("films")
    suspend fun getAllGhibliFilms(): List<GetAllGhibliFilmResponseItem>
}