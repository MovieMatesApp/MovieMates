package com.example.moviemates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moviemates.movieModels.EventModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class MainActivity3 : AppCompatActivity() {
    private val eventModelList = mutableListOf<EventModel>()
    val firebaseAuth = FirebaseAuth.getInstance()
    val db = FirebaseFirestore.getInstance()
    val eventsCollection = db.collection("events")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        fetchEventsFromFirestore()
        showToast(eventModelList.size.toString())
    }
    private fun fetchEventsFromFirestore() {
        // Assuming you want to fetch all events from the "events" collection
        eventsCollection.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val querySnapshot: QuerySnapshot? = task.result
                if (querySnapshot != null) {
                    for (document in querySnapshot.documents) {
                        val event = document.toObject(EventModel::class.java)
                        if (event != null) {
                            eventModelList.add(event)
                        }
                    }
                  //  eventAdapter.notifyDataSetChanged()
                }
            } else {
                // Handle errors
            }
        }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    companion object {
        private const val TAG = "MainActivity3"
    }
}


