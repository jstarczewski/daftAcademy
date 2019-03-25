package pl.daftacademy.androidlevelup.view.movies

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.daftacademy.androidlevelup.entity.Movie

object MoviesBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:movies")
    fun setMovies(recyclerView: RecyclerView, movies: ArrayList<Movie>) {
        with(recyclerView.adapter as MoviesAdapter) {
            items = movies
        }
    }

}