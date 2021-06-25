package com.dwtraining.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dwtraining.database.MoviesDatabase.Companion.DATABASE_VERSION
import com.dwtraining.models.Movie

/**
 * @author Giovani González
 * Created by Giovani on 2020-10-21.
 */
@Database(entities = [Movie::class], version = DATABASE_VERSION)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MovieDAO

    companion object {
        const val DATABASE_NAME = "MoviesDatabase"
        const val DATABASE_VERSION = 1
    }
}