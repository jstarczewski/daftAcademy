package pl.daftacademy.androidlevelup.view.viewmodel

import android.app.Application
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import pl.daftacademy.androidlevelup.data.MovieDao
import pl.daftacademy.androidlevelup.data.MovieFileDao
import pl.daftacademy.androidlevelup.entity.Movie

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val _movies = ArrayList<Movie>()
    val movies: ObservableArrayList<Movie> = ObservableArrayList()
    private val movieDao: MovieDao = MovieFileDao(getApplication())

    fun start() {
        if (movies.isEmpty()) {
            load()
            movies.addAll(_movies)
        }
    }

    private fun load() = _movies.addAll(movieDao.getAllMovies())

    fun filter(filtering: String) {
        movies.clear()
        if (filtering != "All movies")
            movies.addAll(_movies.filter { it.genres.contains(filtering) })
        else
            movies.addAll(_movies)
    }

}
