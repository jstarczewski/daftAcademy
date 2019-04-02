package pl.daftacademy.androidlevelup.database

import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.mock
import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.database.Movie as dbMovie

@RunWith(JUnit4::class)
class RoomMoviesTest {

    private val movieDao: MovieDao = mock(MovieDao::class.java)
    private val studioDao: StudioDao = mock(StudioDao::class.java)
    private val movie: dbMovie = mock(dbMovie::class.java)
    private val studio: Studio = mock(Studio::class.java)
    // roomMovies : RoomMovies aby zrobic testy metod nie wystawionych na zewnatrz przez interfejs
    private val roomMovies: RoomMovies = RoomMovies(movieDao, studioDao)

    private val moviesList : ArrayList<dbMovie> = ArrayList()

    @Before
    fun setUp() {
        Mockito.`when`(movieDao.add(listOf(movie))).thenAnswer{
            moviesList.add(movie)
        }
        Mockito.`when`(studioDao.add(studio)).thenReturn(0)
    }

    @Test
    fun add() {
        roomMovies.add(listOf(Movie("Film", 1922, listOf("Horror"), "Studio")))
        println(moviesList.toString())
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