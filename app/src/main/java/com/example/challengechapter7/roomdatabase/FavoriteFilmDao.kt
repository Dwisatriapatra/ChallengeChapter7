package com.example.challengechapter7.roomdatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.challengechapter7.model.FavoriteFilm

@Dao
interface FavoriteFilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm)

    @Query("SELECT * FROM favorite_ghibli_film")
    fun getFavoriteFilm() : List<FavoriteFilm>

    @Query("DELETE FROM favorite_ghibli_film WHERE id = :id")
    suspend fun deleteFavoriteFilmById(id: Int)
}