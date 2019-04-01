package pl.daftacademy.androidlevelup.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    fun add(movies: Collection<Movie>)

    @Query("SELECT *, studio.name FROM movie INNER JOIN studio ON studio.id = movie.studioId")
    fun get(): List<Movie>
}
