package com.dwtraining.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dwtraining.models.Movie
import com.dwtraining.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Giovani González
 * Created by Giovani on 2020-10-27.
 */
@HiltViewModel
class MovieDetailViewModel @Inject constructor(private val repository: MoviesRepository) : ViewModel() {
    var selectedMovie: MutableLiveData<Movie> = MutableLiveData()

    fun getMovie(id: Long) = viewModelScope.launch {
        val movie = repository.getMovieBy(id)
        selectedMovie.postValue(movie)
    }

    fun getRating(rating: Double) = rating.toInt()
}