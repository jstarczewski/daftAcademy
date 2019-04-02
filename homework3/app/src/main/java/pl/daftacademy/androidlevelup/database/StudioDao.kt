package pl.daftacademy.androidlevelup.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudioDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(studio: Studio) : Long

    @Query("SELECT name FROM Studio WHERE id = :id")
    fun getStudioNameById(id: Int) : String

    @Query("SELECT * FROM Studio")
    fun getStudios() : List<Studio>

    @Query("SELECT id FROM Studio WHERE name = :name")
    fun getStudioIdByName(name: String) : Int


}