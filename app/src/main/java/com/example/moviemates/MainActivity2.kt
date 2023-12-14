package com.example.moviemates

import CommentAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.databinding.ActivityMain2Binding
import com.example.moviemates.movieModels.MovieComments
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var commentAdapter: CommentAdapter
    private lateinit var myItems: ArrayList<MovieComments>
    private val comments = mutableListOf<String>()
    private val db = FirebaseFirestore.getInstance()
    private lateinit var userEmail: String
    private lateinit var userId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        myItems = ArrayList()
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""
        binding.textViewEmail.text ="Welcome, $userEmail!"

        val firebaseAuth = FirebaseAuth.getInstance()
        val eventsCollection = db.collection("comments")

        val recyclerView: RecyclerView = findViewById(R.id.comment_recyclerview)
        commentAdapter = CommentAdapter(myItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = commentAdapter

        binding.button12.setOnClickListener {

            val myComment = binding.addComment.text.toString()


            if (myComment.isNotEmpty()) {

                val commentData = hashMapOf(
                    "comment" to myComment,
                    "userId" to userEmail
                )


                eventsCollection.add(commentData)
                    .addOnSuccessListener { documentReference ->
                        showToast("Comment added successfully with ID: ${documentReference.id}")
                        comments.add(myComment)
                        commentAdapter.notifyDataSetChanged()
                        binding.addComment.setText("")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                    .addOnFailureListener { e ->
                        showToast("Error adding comment: $e")
                    }
            } else {
                showToast("Please enter a comment before adding.")
            }
        }


        eventsCollection.get().addOnSuccessListener { result ->
            for (document in result) {
                val comment = document["comment"] as String
                val usermyId = document["userId"]  as? String
                comments.add(comment)

                println("helllo"+usermyId)
                var  out = MovieComments(
                    userId = usermyId.toString(),
                    comment = comment
                )

                myItems.add(out)
            }
            println("sizeofItem:"+myItems.size.toString())

            commentAdapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->
            showToast("Error getting comments: $e")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity2"
    }
}