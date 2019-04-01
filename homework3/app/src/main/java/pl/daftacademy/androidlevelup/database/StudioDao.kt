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
    fun getStudioName(id: Int) : String
}