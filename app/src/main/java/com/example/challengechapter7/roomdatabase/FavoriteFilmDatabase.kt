package com.example.challengechapter7.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.challengechapter7.model.FavoriteFilm

//database class for favorite film list
@Database(entities = [FavoriteFilm::class], version = 1, exportSchema = false)
abstract class FavoriteFilmDatabase : RoomDatabase() {
    abstract fun favoriteFilmDao(): FavoriteFilmDao

    companion object {
        private var INSTANCE: FavoriteFilmDatabase? = null

        fun getInstance(context: Context): FavoriteFilmDatabase? {
            if (INSTANCE == null) {
                synchronized(FavoriteFilmDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        FavoriteFilmDatabase::class.java, "FavoriteFilm.db"
                    ).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }
}