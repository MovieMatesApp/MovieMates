package com.example.moviemates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        redirectAfterDelay()
    }
    private fun redirectAfterDelay() {
        val delayMillis = 5000L


        val handler = android.os.Handler()
        handler.postDelayed({
            // Start the main activity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)


            finish()
        }, delayMillis)
    }
}