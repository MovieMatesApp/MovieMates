package com.example.moviemates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

private const val TAG = "MainActivity/"
private const val SEARCH_API_KEY = "c30b6be13072568f3198912087cdda39"
private const val ARTICLE_SEARCH_URL = "https://api.themoviedb.org/3/tv/top_rated?api_key="
class MoviesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view=inflater.inflate(R.layout.movies_fragment,container,false)
        //val textView: TextView =view.findViewById(R.id.textViewHome)
        // Customize the fragment view as needed
        return view
    }
}
