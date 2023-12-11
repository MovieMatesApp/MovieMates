package com.example.moviemates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviemates.databinding.ActivityDashboardBinding


class Dashboard : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var userEmail: String
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""
        binding.welcomeMessage.text = "Welcome, $userEmail!"


        binding.MyEventBtn.setOnClickListener{
            val intent = Intent(this, EventActivity::class.java)
            intent.putExtra("USER_EMAIL", userEmail)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
        }

    }

}