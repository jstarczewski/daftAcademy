package pl.daftacademy.androidlevelup.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MovieDao {

    @Insert
    fun add(movies: Collection<Movie>)

    @Query("SELECT * FROM Movie")
    fun get(): List<Movie>
}
