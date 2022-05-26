package com.example.challengechapter7.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.challengechapter7.model.FavoriteFilm
import com.example.challengechapter7.roomdatabase.FavoriteFilmDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteFilmViewModel @Inject constructor(favoriteFilmDao: FavoriteFilmDao) : ViewModel(){
    private val liveDataFavoriteFilm = MutableLiveData<List<FavoriteFilm>>()
    var favoriteFilm : LiveData<List<FavoriteFilm>> = liveDataFavoriteFilm
    private val dao = favoriteFilmDao
    init {
        viewModelScope.launch {
            val dataFavoriteFilm = favoriteFilmDao.getFavoriteFilm()
            liveDataFavoriteFilm.value = dataFavoriteFilm
        }
    }

    fun insertNewFavoriteFilm(favoriteFilm: FavoriteFilm){
        viewModelScope.launch {
            dao.insertFavoriteFilm(favoriteFilm)
        }
    }

    fun deleteFavoriteFilm(id : Int){
        viewModelScope.launch {
            dao.deleteFavoriteFilmById(id)
        }
    }
}