package com.example.moviemates.movieModels

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONArray
import org.json.JSONObject

data class MovieInfo(
    val id: Long,
    @SerializedName("original_title") val movieTitle: String,
    @SerializedName("poster_path") val posterPath: String,
    val overview: String
)

