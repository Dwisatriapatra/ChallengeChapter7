package com.example.challengechapter7.network

import com.example.challengechapter7.model.GetAllUserResponseItem
import com.example.challengechapter7.model.PostNewUser
import com.example.challengechapter7.model.RequestUser
import retrofit2.Call
import retrofit2.http.*

interface ApiUserServices {
    @GET("datauserlogin")
    suspend fun getAllUser() : List<GetAllUserResponseItem>
    @POST("datauserlogin")
    fun addDataUser(@Body reqUser : RequestUser) : Call<PostNewUser>
//    @PUT("datauserlogin/{id}")
//    fun updateDataUser(
//        @Path("id") id : String,
//        @Body request : RequestUser
//    ) : Call<List<GetAllUserResponseItem>>
}