package com.dwtraining.database

import androidx.room.*
import com.dwtraining.models.Movie

/**
 * @author Giovani González
 * Created by Giovani on 2020-10-21.
 */
@Dao
interface MovieDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<Movie>)

    @Query("SELECT * FROM Movies")
    suspend fun loadAllMovies(): List<Movie>

    @Query("SELECT * FROM Movies WHERE id = :idMovie")
    suspend fun getMovieById(idMovie: Long): Movie

    @Delete
    suspend fun deleteMovies(movies: List<Movie>)
}