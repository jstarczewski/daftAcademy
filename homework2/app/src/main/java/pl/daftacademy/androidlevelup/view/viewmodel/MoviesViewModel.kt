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

    var filtering: (String) -> (Unit) = { filtering ->
        movies.clear()
        movies.addAll(_movies.filter { it.genres.contains(filtering) })
    }

    fun start() {
        if (movies.isEmpty())
            _movies.addAll(movieDao.getAllMovies())
    }

    private fun getMovies() = movieDao.getAllMovies()

}
