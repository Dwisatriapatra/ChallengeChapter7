package com.example.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapter7.model.GetAllUserResponseItem
import com.example.challengechapter7.model.PostNewUser
import com.example.challengechapter7.model.RequestUser
import com.example.challengechapter7.network.ApiUserServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(api : ApiUserServices) : ViewModel(){
    private val liveDataUser = MutableLiveData<List<GetAllUserResponseItem>>()
    val user : LiveData<List<GetAllUserResponseItem>> = liveDataUser
    private val apiServices = api
    init {
        viewModelScope.launch {
            val dataUser = api.getAllUser()
            delay(2000)
            liveDataUser.value = dataUser
        }
    }
    fun insertNewUser(requestUser: RequestUser) : Boolean{
        var messageResponse = false
        viewModelScope.launch {
            apiServices.addDataUser(requestUser)
                .enqueue(object : Callback<PostNewUser>{
                    override fun onResponse(
                        call: Call<PostNewUser>,
                        response: Response<PostNewUser>
                    ) {
                        messageResponse = response.isSuccessful
                    }

                    override fun onFailure(call: Call<PostNewUser>, t: Throwable) {
                        messageResponse = false
                    }

                })
        }
        return messageResponse
    }
    fun updateDataUser(id: String, requestUser: RequestUser) : Boolean{
        var messageResponse = false
        viewModelScope.launch {
            apiServices.updateDataUser(id, requestUser)
                .enqueue(object : Callback<List<GetAllUserResponseItem>>{
                    override fun onResponse(
                        call: Call<List<GetAllUserResponseItem>>,
                        response: Response<List<GetAllUserResponseItem>>
                    ) {
                        messageResponse = response.isSuccessful
                    }

                    override fun onFailure(call: Call<List<GetAllUserResponseItem>>, t: Throwable) {
                        messageResponse = false
                    }

                })
        }
        return messageResponse
    }
}