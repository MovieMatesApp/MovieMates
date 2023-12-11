package com.example.moviemates.movieModels

import com.google.gson.Gson

// MovieInfoJsonParser.kt


object MovieInfoJsonParser {

    fun parseMovieInfoList(jsonString: String): List<MovieInfo> {
        return Gson().fromJson(jsonString, Array<MovieInfo>::class.java).toList()
    }
}

