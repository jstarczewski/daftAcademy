package pl.daftacademy.androidlevelup.view.movies

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.daftacademy.androidlevelup.entity.Movie

object MoviesBindingAdapter {

    @JvmStatic
    @BindingAdapter("app:items")
    fun setItems(recyclerView: RecyclerView, movies: ArrayList<Movie>) {
        with(recyclerView as MoviesAdapter) {
            items = movies
        }
    }

}