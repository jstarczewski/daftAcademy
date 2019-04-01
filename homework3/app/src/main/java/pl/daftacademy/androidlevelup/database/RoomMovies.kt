package pl.daftacademy.androidlevelup.database

import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.entity.Movies
import pl.daftacademy.androidlevelup.database.Movie as DbMovie

class RoomMovies(private val movieDao: MovieDao, private val studioDao: StudioDao) : Movies {

    override fun add(movies: Collection<Movie>) = movieDao.add(movies.map {
        pl.daftacademy.androidlevelup.database.Movie(
            0, it.title, it.year, it.genres.joinToString(","),
            (studioDao.add(Studio(0, it.studio.toString()))).toInt()
        )
    })

    override fun get(): List<Movie> = movieDao.get().map { movie: pl.daftacademy.androidlevelup.database.Movie ->
        Movie(movie.title, movie.year, movie.genres.split(','), studioDao.getStudioName(movie.studioId))
    }
}
