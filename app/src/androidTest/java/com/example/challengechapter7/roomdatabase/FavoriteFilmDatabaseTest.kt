package com.example.challengechapter7.roomdatabase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteFilmDatabaseTest : TestCase(){

    private lateinit var db : FavoriteFilmDatabase
    private lateinit var dao : FavoriteFilmDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, FavoriteFilmDatabase::class.java).build()
        dao = db.favoriteFilmDao()
    }

    @After
    public override fun tearDown(){
        db.close()
    }

    @Test
    fun getFavoriteFilmTest(){
        val result = dao.getFavoriteFilm()
    }
}