package com.example.moviemates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

private const val TAG = "DetailActivity"
class MovieDetailFragment: Fragment() {
    private lateinit var mediaImageView: ImageView
    private lateinit var titleTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var overviewTextView: TextView
    private lateinit var languageTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.movie_view_fragment,container,false)


        mediaImageView = view.findViewById(R.id.mediaImage)
        titleTextView = view.findViewById(R.id.mediaTitle)
        dateTextView = view.findViewById(R.id.mediaDate)
        overviewTextView = view.findViewById(R.id.mediaOverview)


        val movie = arguments?.getSerializable(MOVIE_EXTRA) as Movie

        titleTextView.text = movie.name
        dateTextView.text = movie.first_air_date
        overviewTextView.text = movie.overview
        languageTextView.text = movie.original_language
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/w500" + movie.backdrop_path)
            .into(mediaImageView)
        return view
    }
}