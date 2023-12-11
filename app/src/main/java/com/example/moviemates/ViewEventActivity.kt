package com.example.moviemates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.moviemates.movieModels.EventModel
import com.google.firebase.firestore.FirebaseFirestore
import java.util.Date

class ViewEventActivity : AppCompatActivity() {
    val db = FirebaseFirestore.getInstance()
    val eventsCollection = db.collection("events")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_event)

        eventsCollection.get()
            .addOnSuccessListener { querySnapshot ->



                for (document in querySnapshot) {
                    val eventData = document.data
                    val myComment = eventData["comment"] as String
                    val userId = eventData["userId"] as String
                    val userEmail = eventData["userEmail"] as String
                    val eventDate = eventData["eventDate"] as Date
                    println(myComment)
                    val eventModel = EventModel(myComment, userId, userEmail, eventDate.toString())

                }




            }
            .addOnFailureListener { e ->
                val errorMessage = "Error retrieving events: $e"


                // Print the error message directly to the console
                println(errorMessage)
            }
    }
}