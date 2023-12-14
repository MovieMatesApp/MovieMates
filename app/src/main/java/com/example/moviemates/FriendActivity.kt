package com.example.moviemates

import UserAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.databinding.ActivityEventBinding
import com.example.moviemates.databinding.ActivityFriendBinding
import com.example.moviemates.movieModels.EventModel
import com.example.moviemates.movieModels.friendModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.util.ArrayList


class FriendActivity : AppCompatActivity() {

    private lateinit var userEmail: String
    private lateinit var userId: String
    private val db = FirebaseFirestore.getInstance()
    private lateinit var commentAdapter: UserAdapter
    private lateinit var myItems: ArrayList<friendModel>
    private lateinit var binding: ActivityFriendBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend) // Initialize RecyclerView
        myItems = ArrayList()
        userEmail = intent.getStringExtra("USER_EMAIL") ?: ""
        userId = intent.getStringExtra("USER_ID") ?: ""
        val eventsCollection = db.collection("users")

        println("Welcome, $userEmail!")

        eventsCollection.get().addOnSuccessListener { result ->
            for (document in result) {
                val emaildb = document["email"] as String
                val useriddb = document["userid"]  as? String

                if(useriddb!=userId){

                    var  out = friendModel(
                        friendId = useriddb.toString(),
                        friendEmail = emaildb.toString(),
                        userid = userId.toString(),
                        myemail = userEmail.toString()
                    )

                    myItems.add(out)

                }

            }
            println("sizeofItem12:"+myItems.size.toString())
            val recyclerView: RecyclerView = findViewById(R.id.friend_RecyclerView)
            commentAdapter = UserAdapter(myItems)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = commentAdapter
             commentAdapter.notifyDataSetChanged()
        }.addOnFailureListener { e ->

        }




    }

    public fun isUserAlreadyFriend(friendId: String, callback: (Boolean) -> Unit) {
        val friendsCollection = db.collection("friends")
        val query = friendsCollection.whereEqualTo("user_id", userId).whereEqualTo("friend_id", friendId)

        query.get().addOnSuccessListener { result ->
            callback(!result.isEmpty)
        }.addOnFailureListener { e ->
            // Handle failure
            callback(false)
        }
    }



}