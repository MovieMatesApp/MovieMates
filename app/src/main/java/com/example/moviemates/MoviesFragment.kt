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
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
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
        client.get(MOVIE_SEARCH_URL + SEARCH_API_KEY, object : JsonHttpResponseHandler() {
            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e(TAG, "Failed to fetch articles: $statusCode")
            }

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.i(TAG, "Successfully fetched articles: $json")
                try {
                    val searchMovieResponse = Json.decodeFromString<SearchMovieResponse>(json.jsonObject.toString())

                    // Check if the list is not null before using it
                    if (searchMovieResponse.docs != null) {
                        // Add movies to the existing list or use it as needed
                        movies.addAll(searchMovieResponse.docs)

                        // Now you have the list of movies in the 'moviesList'
                        // Do whatever you need to do with the moviesList
                    }
                    movieAdapter.notifyDataSetChanged()

                } catch (e: JSONException) {
                    Log.e(TAG, "Exception: $e")
                }
            }

        })
        return view
    }
}
