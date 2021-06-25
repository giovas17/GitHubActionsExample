package com.dwtraining.repositories

import android.util.Log
import com.dwtraining.database.MoviesDatabase
import com.dwtraining.interfaces.MoviesAPIService
import com.dwtraining.models.Movie
import com.dwtraining.network.Connectivity
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val service: MoviesAPIService,
                       private val database: MoviesDatabase) {

    suspend fun getMovies(): List<Movie> {
        try {
            return if (Connectivity.isConnected) {
                val response = getMoviesFromAPI()
                if (response.isSuccessful && response.body() != null) {
                    val movies = response.body() as List<Movie>
                    database.moviesDao().insertMovies(movies)
                    movies
                } else {
                    Log.e(TAG, "Error occurred: ${response.message()}")
                    emptyList()
                }
            } else {
                getMoviesFromDatabase()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Something went wrong, $e")
        }
        return emptyList()
    }

    private suspend fun getMoviesFromAPI() = service.getAllMovies()

    private suspend fun getMoviesFromDatabase() = database.moviesDao().loadAllMovies()

    suspend fun getMovieBy(id: Long) = database.moviesDao().getMovieById(id)

    private companion object {
        val TAG: String = MoviesRepository::class.java.simpleName
    }
}