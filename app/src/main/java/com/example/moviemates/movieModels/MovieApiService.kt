package com.example.moviemates.movieModels

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface MovieApiService {
    @GET("movie/popular")
    fun getPopularMovies(@Query("api_key") apiKey: String): Call<List<MovieInfo>>
}
