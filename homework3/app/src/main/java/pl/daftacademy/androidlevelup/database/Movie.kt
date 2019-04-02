package pl.daftacademy.androidlevelup.database

import androidx.room.*

/**
 * Indeksowanie usuwa warning wystepujacy podczas budowania projektu
 * */

@Entity(
    indices = [Index(value = ["studioId"], name = "id_movie_studio")],
    foreignKeys = [
        ForeignKey(
            entity = Studio::class,
            parentColumns = ["id"],
            childColumns = ["studioId"],
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
class Movie(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,
    val title: String,
    val year: Int,
    val genres: String,
    @ColumnInfo(name = "studioId")
    val studioId: Int
)

