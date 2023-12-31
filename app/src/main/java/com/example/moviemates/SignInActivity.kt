package com.example.moviemates

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemates.databinding.ActivitySignInBinding
import com.example.moviemates.databinding.ActivitySignUpBinding
import com.example.moviemates.movieModels.MovieComments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize FirebaseAuth
        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonGoToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        // Set click listener for the sign-in button
        binding.buttonSignIn.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val email = binding.editTextEmail.text.toString()
        val password = binding.editTextPassword.text.toString()
        val userCollection = db.collection("users")
        if (email.isNotEmpty() && password.isNotEmpty()) {
            // Use FirebaseAuth to sign in with email and password
            userCollection.get().addOnSuccessListener { result ->
                for (document in result) {
                    val emaildb = document["email"] as String
                    val passworddb = document["password"]  as? String
                    val userId = document["userid"]  as? String

                    if(email== emaildb && password==passworddb){

                        showToast("Login Successful")

                        val intent = Intent(this, Dashboard::class.java)
                        intent.putExtra("USER_EMAIL", email)
                        intent.putExtra("USER_ID", userId)
                        startActivity(intent)
                    }

                }



            }.addOnFailureListener { e ->
                showToast("Error getting comments: $e")
            }



      /*      firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    if (it.isSuccessful) {
                        // Sign-in success
                        Log.d("SignInActivity", "signInWithEmail:success")
                        val user = firebaseAuth.currentUser
                        val userEmail = user?.email
                        val userId = user?.uid

                        showToast("Login Successful")

                        val intent = Intent(this, Dashboard::class.java)
                        intent.putExtra("USER_EMAIL", userEmail)
                        intent.putExtra("USER_ID", userId)
                        startActivity(intent)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("SignInActivity", "signInWithEmail:failure", it.exception)
                        // You can display an error message to the user, handle specific errors, etc.
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
                }*/
        } else {
            // Email or password is empty
            // You can display an error message to the user
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        private const val TAG = "SignInActivity"
    }
}
