package pl.daftacademy.androidlevelup.database

import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.entity.Movies
import pl.daftacademy.androidlevelup.database.Movie as DbMovie

class RoomMovies(private val movieDao: MovieDao, private val studioDao: StudioDao) : Movies {

    /**
     * Zdecydowałem się na wstrzyknięcie StudioDao do RoomMovies, które niejako jest traktowane jako repozytorium.
     * Biorąc pod uwagę, że teraz klasa RoomMovies łamie single responsibility principale i troche DDD mogłaby zostać rozdzielona na separatywne
     * repozytoria, ale w przypadku zadania domowego oba nasze entity sa ze sobą ściśle powiązane i mają wspólny powód do zmiany a sam projekt nie będzie
     * prawdopodobnie dalej rozszerzany o nowe przypadki uzycia.
     *
     * Rozwiązanie to pozwala na implementację zadanych funkcjonalności i nie wprowadzanie znacznych zmian w pakietach .entity, .view
     * Jedyną zmianą jaką wprowadziłem (w pakiecie .view) było wstrzyknięcie studioDao do konstruktora w fabryce abstrakcyjnej ViewModeli, aby
     * testowanie było łatwiejsze.
     *
     * Jeżeli łaczymy tabele po kluczach głównych zbudowanych na podstawie auto-generowanego id to za kazdym razem gdy dodajemy film
     * to tworzy nam sie nowa wytwórnia filmowa o innym id i tej samej nazwie, która z punktu widzenia sql jest oddzielnym rekordem,
     * będącym w relacji N:1 z filmem, która zawsze bedzie 1:1 (bo nie mamy wpływu na auto-generowany klucz). Przyjąłem, że wytwórnie rozróżniamy po nazwie,
     * co ma wiekszy sens w kontekscie zadania. Przyjąłem również, ze filmy rozróżnia sie po id, dlatego mogą powstać filmy o takich samych parametrach.
     *
     * */

    override fun add(movies: Collection<Movie>) {
        movieDao.add(movies.map {
            DbMovie(0, it.title, it.year, it.genres.joinToString(","), getStudioIdAfterInsert(it.studio.toString()))
        })
        // Log-testy użytkownika koncowego pokazujace zawartość bazy po operacji
        // Log.e("Studios ->", studioDao.getStudios().toString())
        // Log.e("Movies ->", movieDao.get().map { Movie(it.title, it.year, listOf(it.genres), "Movie:${it.id} Studio:${it.studioId}")}.toString())
    }

    override fun get(): List<Movie> = movieDao.get().map {
        Movie(it.title, it.year, it.genres.split(','), getStudioNameById(it.studioId))
    }

    private fun getStudioIdAfterInsert(name: String) = studioDao.add(Studio(getStudioIdByName(name), name)).toInt()

    private fun getStudioIdByName(name: String) = studioDao.getStudioIdByName(name)

    private fun getStudioNameById(id: Int) = studioDao.getStudioNameById(id)

}
