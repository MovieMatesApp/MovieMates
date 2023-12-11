package com.example.moviemates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.Adapters.MovieInfoAdapter
import com.example.moviemates.databinding.ActivityDashboardBinding
import com.example.moviemates.movieModels.MovieInfo
import com.example.moviemates.movieModels.MovieInfoJsonParser
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var userEmail: String
    private lateinit var userId: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        binding = ActivityDashboardBinding.inflate(layoutInflater)




        // Assuming you have the actual JSON response as a string
        val jsonString = "[{\"id\": 1, \"original_title\": \"Movie 1\", \"poster_path\": \"/path1.jpg\", \"overview\": \"Overview 1\"}," +
                "{\"id\": 2, \"original_title\": \"Movie 2\", \"poster_path\": \"/path2.jpg\", \"overview\": \"Overview 2\"}]"

// Use Gson to convert JSON to List<MovieInfo>
        val movieInfoList: List<MovieInfo> = Gson().fromJson(jsonString, object : TypeToken<List<MovieInfo>>() {}.type)

// Now you can use movieInfoList as needed, for example, set it to the RecyclerView adapter
        val recyclerView: RecyclerView = findViewById(R.id.movie_info_recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = MovieInfoAdapter(this, movieInfoList)







        setContentView(binding.root)
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""
        binding.welcomeMessage.text = "Welcome, $userEmail!"
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, MoviesFragment())
                .commit()}

        binding.myEventBtn.setOnClickListener{
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("USER_EMAIL", userEmail)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

    }

}

