package com.example.moviemates

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EventActivity : AppCompatActivity() {
    private val TAG: String = EventActivity::class.java.simpleName
    private lateinit var myBtnEvent:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event)

        // Use Firebase authentication instead of FirebaseAuth for databaseReference
        val firebaseAuth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("events")

        val eventNameEditText: EditText = findViewById(R.id.editTextEventName)
        val datePicker: DatePicker = findViewById(R.id.datePicker)
        myBtnEvent = findViewById(R.id.eventBtn)

        myBtnEvent.setOnClickListener {
            Log.d(TAG, "Button clicked")
            showToast("Hello world")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
