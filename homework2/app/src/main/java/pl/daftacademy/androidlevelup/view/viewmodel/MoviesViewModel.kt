package pl.daftacademy.androidlevelup.view.viewmodel

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import pl.daftacademy.androidlevelup.data.MovieDao
import pl.daftacademy.androidlevelup.data.MovieFileDao
import pl.daftacademy.androidlevelup.entity.Movie

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = ArrayList<Movie>()
    val movies: ObservableArrayList<Movie> = ObservableArrayList()
    private val movieDao: MovieDao = MovieFileDao(getApplication())

    /**
     * query and cache data from data source (normally with an async solution)
     * */

    fun start() {
        if (_movies.isEmpty()) {
            load()
            movies.addAll(_movies)
        }
    }

    private fun load() = _movies.addAll(movieDao.getAllMovies())

    /**
     * Filter movies by genre
     * */

    fun getMoviesByGenre(filtering: String) {
        movies.clear()
        movies.addAll(_movies.filter { it.genres.contains(filtering) })
    }

    /**
     * Displays all movies
     * */

    fun getAllMovies() {
        movies.clear()
        movies.addAll(_movies)
    }

}
