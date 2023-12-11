package com.example.moviemates.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviemates.R
import com.example.moviemates.movieModels.MovieInfo

class MovieInfoAdapter(private val context: Context, private val movieInfoList: List<MovieInfo>) :
    RecyclerView.Adapter<MovieInfoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_info, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movieInfo = movieInfoList[position]

        // Set movie details to the views
        holder.titleTextView.text = movieInfo.movieTitle
        holder.overviewTextView.text = movieInfo.overview

        // Load poster image using Glide library
        Glide.with(context)
            .load("https://image.tmdb.org/t/p/w500${movieInfo.posterPath}")
            .into(holder.posterImageView)
    }

    override fun getItemCount(): Int {
        return movieInfoList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val overviewTextView: TextView = itemView.findViewById(R.id.overviewTextView)
        val posterImageView: ImageView = itemView.findViewById(R.id.posterImageView)
    }
}
