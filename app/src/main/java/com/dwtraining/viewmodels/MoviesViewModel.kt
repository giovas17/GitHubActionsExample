package com.dwtraining.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwtraining.models.Movie
import com.dwtraining.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Giovani González
 * Created by Giovani on 2020-06-22.
 */
@HiltViewModel
class MoviesViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {

    private lateinit var movies: MutableLiveData<List<Movie>>
    var isRefreshing: MutableLiveData<Boolean> = MutableLiveData()

    @JvmOverloads
    fun getMovies(reloadAgain: Boolean = false): LiveData<List<Movie>> {
        if (!::movies.isInitialized) {
            movies = MutableLiveData()
            getMoviesFromRepository()
        }
        if (reloadAgain) {
            getMoviesFromRepository()
        }
        return movies
    }

    private fun getMoviesFromRepository() {
        isRefreshing.postValue(true)
        viewModelScope.launch(Dispatchers.IO) {
            val moviesJob: Deferred<List<Movie>> = async { repository.getMovies() }
            movies.postValue(moviesJob.await())
            isRefreshing.postValue(false)
        }
    }

    fun sortMoviesByName() = movies.postValue(movies.value?.sortedBy { it.title })

    fun sortMoviesByPopularity() = movies.postValue(movies.value?.sortedByDescending { it.popularity })

    fun sortMoviesByRating() = movies.postValue(movies.value?.sortedByDescending { it.rating })
}