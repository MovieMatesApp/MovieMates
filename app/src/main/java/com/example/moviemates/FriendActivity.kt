package com.example.moviemates

import UserAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class FriendActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var userAdapter: UserAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend) // Initialize RecyclerView
        recyclerView = findViewById(R.id.friend_RecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userAdapter = UserAdapter()
        recyclerView.adapter = userAdapter

        // Check if a user is signed in
        val auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            // If no user is signed in, launch FirebaseUI authentication
            launchFirebaseUIAuth()
        } else {
            // If a user is already signed in, fetch and display users
            fetchAndDisplayUsers()
        }
    }
    private fun launchFirebaseUIAuth() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()
            // Add other authentication providers as needed
        )

        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    private fun fetchAndDisplayUsers() {
        // Fetch all Firebase Authentication users
        val auth = FirebaseAuth.getInstance()
        val users: MutableList<FirebaseUser> = mutableListOf()
    println("ejje")

    }

    // Handle the result of FirebaseUI authentication
    override fun onActivityResult(requestCode: Int, resultCode: Intf, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                fetchAndDisplayUsers()
            } else {
                // Sign in failed
                // Handle the error
            }
        }
    }

    companion object {
        private const val RC_SIGN_IN = 123
    }

}