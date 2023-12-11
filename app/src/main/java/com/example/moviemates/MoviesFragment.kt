package com.example.moviemates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Headers
import org.json.JSONException

fun createJson() = Json {
    isLenient = true
    ignoreUnknownKeys = true
    useAlternativeNames = false
}
private const val TAG = "MoviesFragment/"
private const val SEARCH_API_KEY = "c30b6be13072568f3198912087cdda39"
private const val MOVIE_SEARCH_URL = "https://api.themoviedb.org/3/movie/popular?api_key="

class MoviesFragment : Fragment() {
    private val movies = mutableListOf<Movie>()
    private lateinit var moviesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view= inflater.inflate(R.layout.movies_fragment,container,false)

        moviesRecyclerView = view.findViewById(R.id.movieListRecycleView)
        val movieAdapter =MovieAdapter(requireContext(), movies)
        moviesRecyclerView.adapter = movieAdapter

        moviesRecyclerView.layoutManager = LinearLayoutManager(requireContext()).also {
            val dividerItemDecoration = DividerItemDecoration(requireContext(), it.orientation)
            moviesRecyclerView.addItemDecoration(dividerItemDecoration)
        }
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = SEARCH_API_KEY
        client[
            "https://api.themoviedb.org/3/movie/now_playing",
            params,
            object : JsonHttpResponseHandler()
            {
                /*
                 * The onSuccess function gets called when
                 * HTTP response status is "200 OK"
                 */
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JSON
                ) {
                    // The wait for a response is over

                    //TODO - Parse JSON into Models
                    Log.d("JSON Response", json.toString())
                    val resultsJSON = json.jsonObject.get("results")
                    Log.d("line 74", resultsJSON.toString())
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<Movie>>() {}.type
                    val models: List<Movie> = gson.fromJson(resultsJSON.toString(), arrayMovieType)
                    movies.addAll(models)
                    movieAdapter.notifyDataSetChanged()

                    // Display attributes of each Movie
                    for (movie in models) {
                        Log.d("MoviesFragment", "Title: ${movie.name}")
                        Log.d("MoviesFragment", "Release Date: ${movie.original_language}")
                        Log.d("MoviesFragment", "Release Date: ${movie.overview}")
                        // Add more Log statements for other attributes as needed
                    }

                    // Look for this in Logcat:
                    Log.d("MoviesFragment", "response successful")
                }


                /*
                 * The onFailure function gets called when
                 * HTTP response status is "4XX" (eg. 401, 403, 404)
                 */
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("MoviesFragment", errorResponse)
                    }
                }
            }]
        return view
    }
}
