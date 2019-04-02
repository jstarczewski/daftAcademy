package pl.daftacademy.androidlevelup.database.database

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import pl.daftacademy.androidlevelup.database.MovieDatabase

@RunWith(AndroidJUnit4::class)
class RoomMovieDaoTest {

    private lateinit var moviesDatabase : MovieDatabase

    @Before
    fun setUp() {
        moviesDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, MovieDatabase::class.java).build()
    }

    @Test
    fun addStudioDao() {
        val movieDao = moviesDatabase.movies()

    }

    @Test
    fun get() {
    }

    @Test
    fun getStudioIdAfterInsert() {
    }

    @Test
    fun getStudioNameById() {
    }
}