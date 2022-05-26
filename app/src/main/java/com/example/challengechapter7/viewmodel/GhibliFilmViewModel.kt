package com.example.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapter7.model.GetAllGhibliFilmResponseItem
import com.example.challengechapter7.network.ApiGhibliFilmServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class GhibliFilmViewModel @Inject constructor(@Named("GHIBLI_FILM_DATA") api : ApiGhibliFilmServices) : ViewModel(){
    private val liveDataGhibliFilm = MutableLiveData<List<GetAllGhibliFilmResponseItem>>()
    val ghibliFilm : LiveData<List<GetAllGhibliFilmResponseItem>> = liveDataGhibliFilm
    init {
        viewModelScope.launch {
            val dataGhibliFilm = api.getAllGhibliFilms()
            delay(2000)
            liveDataGhibliFilm.value = dataGhibliFilm
        }
    }
}