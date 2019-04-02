package pl.daftacademy.androidlevelup.database

import androidx.annotation.VisibleForTesting
import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.entity.Movies
import pl.daftacademy.androidlevelup.database.Movie as DbMovie

class RoomMovies(private val movieDao: MovieDao, private val studioDao: StudioDao) : Movies {

    /**
     * Zdecydowałem się na wstrzyknięcie StudioDao do RoomMovies, które niejako jest traktowane jako repozytorium.
     *
     * Biorąc pod uwagę, że teraz klasa RoomMovies łamie single responsibility principale i troche DDD mogłaby zostać rozdzielona na separatywne
     * repozytoria, ale w przypadku zadania domowego oba nasze entity sa ze sobą ściśle powiązane i mają wspólny powód do zmiany a sam projekt nie będzie
     * prawdopodobnie dalej rozszerzany o nowe przypadki uzycia.
     *
     * Rozwiązanie to pozwala na implementację zadanych funkcjonalności i nie wprowadzanie znacznych zmian w pakietach .entity, .view
     * Jedyną zmianą jaką wprowadziłem (w pakiecie .view) było wstrzyknięcie studioDao do konstruktora w fabryce abstrakcyjnej ViewModeli, aby
     * testowanie było łatwiejsze.
     *
     * Biorac pod uwagę dyskusję na slacku dużo metody komunikujące się z bazą znajdują się w jednym miejscu i
     * mozna je prosto owinąć w rozwiazanie pozwalające na asynchroniczne insertowanie/querowanie danych z bazy.
     * Dodatkowo moim zdaniem czasem nadmierny poziom abstrakcji do prostych rzeczy działa tylko na minus.
     *
     * */

    override fun add(movies: Collection<Movie>) =
        movieDao.add(movies.map {
            DbMovie(0, it.title, it.year, it.genres.joinToString(","), getStudioIdAfterInsert(it.studio.toString()))
        })

    override fun get(): List<Movie> = movieDao.get().map {
        Movie(it.title, it.year, it.genres.split(','), getStudioNameById(it.studioId))
    }

    private fun getStudioIdAfterInsert(name: String) = studioDao.add(Studio(0, name)).toInt()

    private fun getStudioNameById(id: Int) = studioDao.getStudioNameById(id)
}
