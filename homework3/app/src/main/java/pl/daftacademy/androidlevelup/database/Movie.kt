package pl.daftacademy.androidlevelup.database

import androidx.room.*

@Entity(
    foreignKeys = [
        ForeignKey(entity = Studio::class, parentColumns = ["id"], childColumns = ["studioId"], onUpdate = ForeignKey.CASCADE)
    ]
)
class Movie(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val year: Int,
    val genres: String,
    val studioId: Int
 //   @Embedded
  //  @Ignore
  //  var studio: Studio

) /*{
    fun toEntity() = pl.daftacademy.androidlevelup.entity.Movie(title, year, genres.split(','), "Studio")

    companion object {
        fun fromEntity(entity: Movie) =
            pl.daftacademy.androidlevelup.database.Movie(0, entity.title, entity.year, entity.genres.joinToString(","), en)
    }
}*/
/*
class MovieAndStudio(
    val id: Int,
    val title: String,
    val year: Int,
    val genres: String,
    @Relation(parentColumn = "id",)

)*/