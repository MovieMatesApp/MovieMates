package com.example.moviemates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.moviemates.movieModels.EventModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.toObject

class MainActivity3 : AppCompatActivity() {
    private lateinit var db: FirebaseFirestore
    private var eventArrayList = ArrayList<EventModel>() // Initialize the ArrayList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        fetchEventsFromFirestore()
    }

    private fun fetchEventsFromFirestore() {
        db = FirebaseFirestore.getInstance()
        db.collection("events").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    // Handle error if needed
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        eventArrayList.add(dc.document.toObject(EventModel::class.java))
                    }
                }
                // Move showToast inside the listener to ensure it's called after the data is loaded
                showToast(eventArrayList.size.toString())
            }
        })
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val TAG = "MainActivity3"
    }
}
