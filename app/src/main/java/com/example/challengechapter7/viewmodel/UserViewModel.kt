package com.example.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapter7.model.GetAllUserResponseItem
import com.example.challengechapter7.network.ApiUserServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(api : ApiUserServices) : ViewModel(){
    private val liveDataUser = MutableLiveData<List<GetAllUserResponseItem>>()
    val user : LiveData<List<GetAllUserResponseItem>> = liveDataUser
    init {
        viewModelScope.launch {
            val dataUser = api.getAllUser()
            delay(2000)
            liveDataUser.value = dataUser
        }
    }
}