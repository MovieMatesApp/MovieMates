package com.example.moviemates

import android.R
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.moviemates.databinding.ActivityEventBinding
import com.example.moviemates.movieModels.MyEvent
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date


class EventActivity : AppCompatActivity() {
    private lateinit var mybinding: ActivityEventBinding
    private lateinit var databaseReference: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mybinding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(mybinding.root)
        databaseReference = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("events")
        mybinding.buttonAddEvent.setOnClickListener{
            var eventName = mybinding.editTextEventName.text.toString()


            var date =mybinding.datePicker
            val year = date.year
            val month = date.month + 1 // Adding 1 because months are 0-indexed

            val dayOfMonth = date.dayOfMonth
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val formattedDate: String = sdf.format(Date(year - 1900, month - 1, dayOfMonth))


            val newEvent = MyEvent(eventName, formattedDate)


            eventsCollection.add(newEvent)
                .addOnSuccessListener { documentReference ->
                    // Document added successfully
                    showToast("DocumentSnapshot added with ID: ${documentReference.id}")

                }
                .addOnFailureListener { e ->
                    showToast("Error: ${e}")

                }

        }

    }
    private fun showToast(message: String) {
        // Implement a method to show a Toast message
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}