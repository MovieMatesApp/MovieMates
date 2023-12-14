package com.example.moviemates

import EventAdapter
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.databinding.ActivityEventBinding
import com.example.moviemates.movieModels.EventModel
import com.example.moviemates.movieModels.MovieComments
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
    private lateinit var myItems: ArrayList<EventModel>
    private lateinit var userEmail: String
    private lateinit var commentAdapter: EventAdapter
    private lateinit var userId: String
    private val eventArrayList = ArrayList<EventModel>()
    val firebaseAuth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        myItems = ArrayList()
        val db = FirebaseFirestore.getInstance()
        val eventsCollection = db.collection("events")

        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""

        val recyclerView: RecyclerView = findViewById(R.id.event_view_list)
        commentAdapter = EventAdapter(myItems)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = commentAdapter
        eventsCollection.get().addOnSuccessListener { result ->
            for (document in result) {
                val comment = document["comment"] as String
                val usermyId = document["userId"]  as? String
                val usermyEmail = document["userEmail"]  as? String
                val eventmyDate = document["eventDate"]  as? String
                println("eventlist check: "+comment)

                var  out = EventModel(
                    userId = usermyId.toString(),
                    comment = comment,
                    userEmail = usermyEmail.toString(),
                    eventDate = eventmyDate.toString()
                )
                myItems.add(out)
            }
            println("sizeofItem12:"+myItems.size.toString())
            commentAdapter.notifyDataSetChanged()
           // commentAdapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->
            showToast("Error getting comments: $e")
        }
        
        
        
        
        
        
        
        
        
        
        
        
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

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun getDateFromDatePicker(datePicker: DatePicker): Date {
        val calendar = Calendar.getInstance()
        calendar.set(datePicker.year, datePicker.month, datePicker.dayOfMonth)
        return calendar.time
    }
}
