package com.example.moviemates.movieModels

data class MyMovie(
    val id: Long,
    val title: String,
    val overview: String,
    val posterPath: String,
    val releaseDate: String,
    val voteAverage: Double
) {
    // Primary constructor parameter if needed
    constructor(
        id: Long,
        title: String,
        overview: String,
        posterPath: String,
        releaseDate: String,
        voteAverage: Double,
        extraParameter: String
    ) : this(id, title, overview, posterPath, releaseDate, voteAverage) {

    }
}
