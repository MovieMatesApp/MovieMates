package com.example.moviemates

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed(Runnable {
            startActivity(Intent(this@SplashScreen, SignInActivity::class.java))
            finish() // close the splash screen to prevent going back
        }, 2000)


    }
}