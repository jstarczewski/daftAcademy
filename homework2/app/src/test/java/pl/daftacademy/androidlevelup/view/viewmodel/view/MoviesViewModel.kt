package pl.daftacademy.androidlevelup.view.viewmodel.view

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.AndroidViewModel
import pl.daftacademy.androidlevelup.data.MovieDao
import pl.daftacademy.androidlevelup.data.MovieFileDao
import pl.daftacademy.androidlevelup.entity.Movie

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Since the given movieDao is not injected via constructor into viewModel I decided to copy App viewModel
     * and create separate one for testing purposes. The belows version uses weaker access modifier for
     * movieDao so it can be mocked
     * */

    private val _movies = ArrayList<Movie>()
    val movies: ObservableArrayList<Movie> = ObservableArrayList()
    var movieDao: MovieDao = MovieFileDao(getApplication())

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

    fun getMoviesByGenre(filtering: String) {
        movies.clear()
        movies.addAll(_movies.filter { it.genres.contains(filtering) })
    }

    fun getAllMovies() {
        movies.clear()
        movies.addAll(_movies)
    }

}
