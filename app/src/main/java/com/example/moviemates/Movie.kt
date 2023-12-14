package com.example.moviemates

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchMovieResponse(
    @SerialName("results")
    val docs: List<Movie>?
)
@Keep
@Serializable
data class Movie(
    @SerialName("poster_path")
    val poster_path: String?,
    @SerialName("backdrop_path")
    val backdrop_path: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("first_air_date")
    val first_air_date: String?,
    @SerialName("name")
    val name: String?,

    @SerialName("original_language")
    val original_language: String?,
):java.io.Serializable {
}