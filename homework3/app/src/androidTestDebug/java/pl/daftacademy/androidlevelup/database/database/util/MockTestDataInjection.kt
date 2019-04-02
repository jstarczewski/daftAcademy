package pl.daftacademy.androidlevelup.view.viewmodel.util

import pl.daftacademy.androidlevelup.entity.Movie

object MockTestDataInjection {

    /**
     * Simple data injection class to easily provide same data set for testing build considering
     * actual data used in app
     * */


    fun provideTestData(): List<Movie> {
        return listOf(
            Movie("Movie 1", 1922, listOf("action"), "Pixar"),
            Movie("Movie 2", 1922, listOf("comedy"), "Pixar"),
            Movie("Movie 3", 1922, listOf("crime"), "Dream Works"),
            Movie("Movie 4", 1922, listOf("horror"), "Spinka Studio"),
            Movie("Movie 5", 1922, listOf("romance", "drama"), "Spinka Studio"),
            Movie("Movie 6", 1922, listOf(), "Pajeet Movie Factory"),
            Movie("Movie 7", 1922, listOf("romance"), "Pajeet Movie Factory")
        )
    }

}