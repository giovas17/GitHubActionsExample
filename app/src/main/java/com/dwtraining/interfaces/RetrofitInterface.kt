package com.dwtraining.interfaces

import com.dwtraining.models.Movie
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author Giovani González
 * Created by Giovani on 2019-06-23.
 */

const val BASE_URL = "https://android-course-api.herokuapp.com/"

interface MoviesAPIService {

    @GET("api/movies")
    suspend fun getAllMovies() : Response<List<Movie>>
}