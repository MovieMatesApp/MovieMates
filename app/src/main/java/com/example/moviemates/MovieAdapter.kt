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
const val USER_EMAIL = "USER_EMAIL"
const val USER_ID = "USER_ID"
private const val TAG = "PersonAdapter"
class MovieAdapter(private val context: Context, private val movies: List<Movie>, private val userId: String,
                   private val userEmail: String) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

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
        private val addPostButton = itemView.findViewById<Button>(R.id.addPost)
        init {
            itemView.setOnClickListener(this)
            addPostButton.setOnClickListener {
                val movieIndex = layoutPosition
                if (movieIndex != RecyclerView.NO_POSITION) {
                    val movie = movies.getOrNull(movieIndex)
                    if (movie != null) {
                        val intent = Intent(context, MainActivity2::class.java)
                        intent.putExtra(MOVIE_EXTRA, movieIndex.toString())

                        intent.putExtra(USER_EMAIL, userEmail)
                        intent.putExtra(USER_ID, userId)
                        Toast.makeText(context, "Clicked on movie at index $movieIndex", Toast.LENGTH_SHORT).show()
                        context.startActivity(intent)
                    }
                }

            }

        }

        override fun onClick(v: View?) {
            val movie = movies.getOrNull(adapterPosition)

            if (position != RecyclerView.NO_POSITION) {
                val movie = movies[position]
                val intent = Intent(context, MainActivity2::class.java)
                intent.putExtra(MOVIE_EXTRA, position)
                intent.putExtra(USER_EMAIL, userEmail)
                intent.putExtra(USER_ID, userId)
                Toast.makeText(context, position, Toast.LENGTH_SHORT).show()


                // Start the new activity
                context.startActivity(intent)
               // val toastMessage = "Selected Movie at position $position: ${movie.overview}"
               // Log.d(TAG, toastMessage)
               // Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
            // Pass necessary data to the new activity using extras


            if (movie != null) {
                val toastMessage = "Selected Movie: ${movie.name}"
                Log.d(TAG, toastMessage)
                Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
            }
        }



        fun bind(flixster: Movie) {
            mediaTitleView.text = flixster.overview

            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + flixster.poster_path)
                .into(mediaImageView)
        }
    }

}