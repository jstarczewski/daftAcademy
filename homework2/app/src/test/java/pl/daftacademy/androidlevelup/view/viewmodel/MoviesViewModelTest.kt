package pl.daftacademy.androidlevelup.view.viewmodel

import android.app.Application
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Test

import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.internal.verification.NoMoreInteractions
import pl.daftacademy.androidlevelup.data.MovieDao
import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.view.viewmodel.util.MockTestDataInjection
import pl.daftacademy.androidlevelup.view.viewmodel.view.MoviesViewModel

@RunWith(JUnit4::class)
class MoviesViewModelTest {

    private val application: Application = mock(Application::class.java)
    private val moviesViewModel: pl.daftacademy.androidlevelup.view.viewmodel.view.MoviesViewModel =
        MoviesViewModel(application)

    @Before
    fun setUpDaoAnswer() {
        moviesViewModel.movieDao = mock(MovieDao::class.java)
        `when`(moviesViewModel.movieDao.getAllMovies()).thenReturn(
            MockTestDataInjection.provideTestData()
        )
        moviesViewModel.start()
    }

    @After
    fun validate() {
        validateMockitoUsage()
    }


    @Test
    fun startQueryNotEmpty() {
        assert(!moviesViewModel.movies.isEmpty())
    }

    @Test
    fun inBeforeFunctionStartInteractionCalled() {
        Mockito.verify(moviesViewModel.movieDao, atMost(1)).getAllMovies()
    }

    @Test
    fun multipleStart() {
        moviesViewModel.start()
        moviesViewModel.start()
        Mockito.verify(moviesViewModel.movieDao, atMost(1)).getAllMovies()
    }

    @Test
    fun filter() {
        moviesViewModel.filter()
        assertEquals(moviesViewModel.movies, MockTestDataInjection.provideTestData())
    }

    @Test
    fun filterWithParameterCorrect() {
        moviesViewModel.filter("horror")
        assertEquals(moviesViewModel.movies, listOf(Movie("Movie 4", 1922, listOf("horror"))))
    }

    @Test
    fun filterWithParameterIncorrect() {
        moviesViewModel.filter("romance")
        assertNotEquals(moviesViewModel.movies, listOf(Movie("Movie 4", 1922, listOf("horror"))))
        moviesViewModel.filter("romance")
        assertEquals(
            moviesViewModel.movies, listOf(
                Movie("Movie 5", 1922, listOf("romance", "drama")),
                Movie("Movie 7", 1922, listOf("romance"))
            )
        )
    }

    @Test
    fun filterWithMoreThanOneAnswerParameterCorrect() {
        moviesViewModel.filter("romance")
        assertEquals(
            moviesViewModel.movies, listOf(
                Movie("Movie 5", 1922, listOf("romance", "drama")),
                Movie("Movie 7", 1922, listOf("romance"))
            )
        )
    }

}