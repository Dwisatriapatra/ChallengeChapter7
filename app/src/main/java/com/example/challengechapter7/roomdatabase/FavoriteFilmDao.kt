package com.example.challengechapter7.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.challengechapter7.model.FavoriteFilm

@Dao
interface FavoriteFilmDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteFilm(favoriteFilm: FavoriteFilm)

    @Query("SELECT * FROM favorite_ghibli_film")
    fun getFavoriteFilm(): List<FavoriteFilm>

    @Query("DELETE FROM favorite_ghibli_film WHERE id = :id")
    suspend fun deleteFavoriteFilmById(id: Int)
}