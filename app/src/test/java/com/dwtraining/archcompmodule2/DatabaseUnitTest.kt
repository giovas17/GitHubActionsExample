package com.dwtraining.archcompmodule2

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import org.junit.Test
import org.junit.Assert.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.dwtraining.database.MovieDAO
import com.dwtraining.database.MoviesDatabase
import com.dwtraining.models.Movie
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseUnitTest {

    private lateinit var movieDAO: MovieDAO
    private lateinit var database: MoviesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, MoviesDatabase::class.java).build()
        movieDAO = database.moviesDao()
    }

    @Test
    fun insertMovies() = runBlocking {
        val movies = generateMovies()
        movieDAO.insertMovies(movies)
        val moviesFromDB = movieDAO.loadAllMovies()
        assertEquals("The list is not the same", movies, moviesFromDB)
    }

    @Test
    fun deleteMovies() = runBlocking {
        val movies = generateMovies()
        movieDAO.insertMovies(movies)
        movieDAO.deleteMovies(movies)
        val moviesFromDB = movieDAO.loadAllMovies()
        assertEquals("The list is not the same", 0, moviesFromDB.size)
    }

    private fun generateMovies() : List<Movie> = listOf(
        Movie("Movie1", 1, "Description Movie 1"),
        Movie("Movie2", 2, "Description Movie 2"),
        Movie("Movie3", 3, "Description Movie 3")
    )
}
