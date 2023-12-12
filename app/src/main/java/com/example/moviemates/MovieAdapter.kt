package com.example.moviemates

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

const val MOVIE_EXTRA = "MOVIE_EXTRA"
private const val TAG = "PersonAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    override fun getItemCount() = movies.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        private val mediaImageView = itemView.findViewById<ImageView>(R.id.mediaImage)
        private val mediaTitleView = itemView.findViewById<TextView>(R.id.mediaTitle)

        init {
            itemView.setOnClickListener(this)

        }

        override fun onClick(v: View?) {
            val movie = movies.getOrNull(adapterPosition)
            val intent = Intent(context, MainActivity2::class.java)

            // Pass necessary data to the new activity using extras
            intent.putExtra(MOVIE_EXTRA, movie)

            // Start the new activity
            context.startActivity(intent)

            if (movie != null) {
                val toastMessage = "Selected Movie: ${movie.name}"
                Log.d(TAG, toastMessage)
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }



        fun bind(flixster: Movie) {
            mediaTitleView.text = flixster.name

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + flixster.poster_path)
                .into(mediaImageView)
        }
    }

}