package com.dwtraining.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class User(
    var id: Int = 0,
    var username: String = "",
    var password: String = "")

@Entity(tableName = "Movies")
data class Movie (
    var title: String = "",
    @PrimaryKey var id: Long = 0,
    var description: String = "",
    var release_date: String = "",
    var genres: String = "",
    var popularity: Double = 0.0,
    @SerializedName("vote_average") var rating: Double = 0.0,
    var vote_count: Int = 0,
    @SerializedName("poster_path") var poster_image_path: String = "",
    var preview_image_path: String = "",
    @SerializedName("backdrop_path") var poster_thumbnail: String = ""
)