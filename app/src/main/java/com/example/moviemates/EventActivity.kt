package com.example.moviemates

import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemates.databinding.ActivityEventBinding
import com.example.moviemates.movieModels.EventModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.util.*

class EventActivity : AppCompatActivity() {
    private val TAG: String = EventActivity::class.java.simpleName
    private lateinit var db: FirebaseFirestore
    private lateinit var binding: ActivityEventBinding
    private lateinit var userEmail: String
    private lateinit var userId: String
    private val eventArrayList = ArrayList<EventModel>()
    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("events")

        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""

        binding.eventBtn.setOnClickListener {
            val myComment = binding.editTextEventName.text.toString()
            val eventDate = getDateFromDatePicker(binding.datePicker)

            if (myComment.isNotEmpty()) {

                val commentData = hashMapOf(
                    "comment" to myComment,
                    "userId" to userId,
                    "userEmail" to userEmail,
                    "eventDate" to eventDate
                )

                eventsCollection.add(commentData)
                    .addOnSuccessListener { documentReference ->
                        showToast("Event added successfully with ID: ${documentReference.id}")
                        showToast(eventArrayList.size.toString())
                        binding.editTextEventName.setText("")

                     // showToast(eventModelList.size.toString())
                    }
                    .addOnFailureListener { e ->
                       // showToast("Error adding comment: $e")
                    }
            } else {
                showToast("Please enter an event before adding.")
            }
        }


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

    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        return calendar.time
    }
}
