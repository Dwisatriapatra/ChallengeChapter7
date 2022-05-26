package com.example.challengechapter7.network

import com.example.challengechapter7.model.GetAllUserResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiUserServices {
    @GET("datauserlogin")
    fun getAllUser() : List<GetAllUserResponseItem>
}