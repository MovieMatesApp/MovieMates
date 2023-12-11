package com.example.moviemates
// Dashboard.kt
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.Adapters.MovieInfoAdapter
import com.example.moviemates.databinding.ActivityDashboardBinding
import com.example.moviemates.movieModels.MovieApiService
import com.example.moviemates.movieModels.MovieInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var userEmail: String
    private lateinit var userId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recyclerView: RecyclerView = findViewById(R.id.movie_info_recycler)

        // Initialize Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create the service using Retrofit
        val movieApiService = retrofit.create(MovieApiService::class.java)

        // Make the API call
        val call = movieApiService.getPopularMovies("c30b6be13072568f3198912087cdda39")

        call.enqueue(object : Callback<List<MovieInfo>> {
            override fun onResponse(call: Call<List<MovieInfo>>, response: Response<List<MovieInfo>>) {
                if (response.isSuccessful) {
                    val movieInfoList = response.body()

                    // Set up RecyclerView with the custom adapter
                    recyclerView.layoutManager = LinearLayoutManager(this@Dashboard)
                    recyclerView.adapter = MovieInfoAdapter(this@Dashboard, movieInfoList ?: emptyList())
                }
            }

            override fun onFailure(call: Call<List<MovieInfo>>, t: Throwable) {
                t.printStackTrace()
            }
        })

        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""
        binding.welcomeMessage.text = "Welcome, $userEmail!"

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MoviesFragment())
                .commit()
        }

        binding.myEventBtn.setOnClickListener {
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("USER_EMAIL", userEmail)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }
    }
}
