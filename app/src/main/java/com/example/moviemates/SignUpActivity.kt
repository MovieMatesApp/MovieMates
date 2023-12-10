package com.example.moviemates

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemates.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance()

       binding.buttonGoToSignIn.setOnClickListener {
           val intent = Intent(this, SignInActivity::class.java)
           startActivity(intent)
       }
        // Set up click listener for sign-up button
        binding.buttonSignUp.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Check if email and password are not empty
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Create user with email and password
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            // Sign up success
                            Log.d(TAG, "createUserWithEmail:success")
                            showToast("Success")
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            // If sign up fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", it.exception)
                            when (it.exception) {
                                is FirebaseAuthInvalidCredentialsException ->
                                    // Handle invalid email or password
                                    showToast("Invalid email or password")
                                is FirebaseAuthUserCollisionException ->
                                    // Handle user already exists
                                    showToast("User with this email already exists")
                                else ->
                                    // Handle other errors
                                    showToast("Sign up failed. Please try again.")
                            }
                        }
                    }
            } else {
                // Display a message if email or password is empty
                showToast("Please enter email and password")
            }
        }
    }

    private fun showToast(message: String) {
        // Implement a method to show a Toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "SignUpActivity"
    }
}
