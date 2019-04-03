package pl.daftacademy.androidlevelup.database

import pl.daftacademy.androidlevelup.entity.Movie
import pl.daftacademy.androidlevelup.entity.Movies
import pl.daftacademy.androidlevelup.database.Movie as DbMovie

class RoomMovies(private val movieDao: MovieDao, private val studioDao: StudioDao) : Movies {

    /**
     * Nie udało mi się wymyśleć w jaki sposób całkowicie odeseparowac od siebie Studio i Movies z zachowaniem narzuconego interfejsu.
     * Z tego powodu nie zaimplementowałem oddzielnego data-source'a z wystawionym interfejsem dla encji Studio. Biorąc pod uwage, że moje rozwiazanie łamie single responsibility principale,
     * niektóre konwencje DDD i obniża poziom abstrakcji to zgodnie z założeniami nie wprowadza znacznych zmian w pakietach .entity, .view, a nasze
     * encje w rozpatrywanym przypadku uzycia maja wspolny powod do zmiany, co troche przemawia za wstrzymaniem sie od implementacji dodatkowych klas.
     * Udało mi się rowniez wyzbyć zbednej logoki z tej warstwy na rzecz operacji na opakowanych w Room kwerendach.
     *
     * Dodatkowo, jeżeli łaczymy tabele po kluczach głównych zbudowanych na podstawie auto-generowanego id to za kazdym razem gdy dodajemy film
     * to tworzy nam sie nowa wytwórnia filmowa o innym id i tej samej nazwie, która z punktu widzenia sql jest oddzielnym rekordem,
     * będącym w relacji N:1 z filmem, która zawsze bedzie 1:1 (bo nie mamy wpływu na auto-generowany klucz). Przyjąłem, że wytwórnie rozróżniamy po nazwie,
     * co ma wiekszy sens w kontekscie zadania. Przyjąłem również, ze filmy rozróżnia sie po id, dlatego mogą powstać filmy o takich samych parametrach.
     *
     * */

    override fun add(movies: Collection<Movie>) =
        movieDao.add(movies.map {
            DbMovie(0, it.title, it.year, it.genres.joinToString(","), getStudioIdAfterInsert(it.studio.toString()))
        })

    override fun get(): List<Movie> = movieDao.get().map {
        Movie(it.title, it.year, it.genres.split(','), getStudioNameById(it.studioId))
    }

    private fun getStudioIdAfterInsert(name: String) = studioDao.add(Studio(getStudioIdByName(name), name)).toInt()

    private fun getStudioIdByName(name: String) = studioDao.getStudioIdByName(name)

    private fun getStudioNameById(id: Int) = studioDao.getStudioNameById(id)

}
