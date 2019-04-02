package pl.daftacademy.androidlevelup.database.database

import pl.daftacademy.androidlevelup.database.MovieDao
import pl.daftacademy.androidlevelup.database.Studio
import pl.daftacademy.androidlevelup.database.StudioDao
import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.entity.Movies
import pl.daftacademy.androidlevelup.database.Movie as DbMovie

class RoomMovies(private val movieDao: MovieDao, private val studioDao: StudioDao) : Movies {


    override fun add(movies: Collection<Movie>) =
        movieDao.add(movies.map {
            DbMovie(0, it.title, it.year, it.genres.joinToString(","), getStudioIdAfterInsert(it.studio.toString()))
        })

    override fun get(): List<Movie> = movieDao.get().map {
        Movie(it.title, it.year, it.genres.split(','), getStudioNameById(it.studioId))
    }

    /**
     * Funkcje prywante aby ułatwić testowanie
     * */

    fun getStudioIdAfterInsert(name: String) = studioDao.add(Studio(0, name)).toInt()

    fun getStudioNameById(id: Int) = studioDao.getStudioNameById(id)
}
