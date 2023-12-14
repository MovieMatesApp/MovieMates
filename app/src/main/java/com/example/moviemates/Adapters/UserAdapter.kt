import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.Dashboard
import com.example.moviemates.MOVIE_EXTRA
import com.example.moviemates.MainActivity2
import com.example.moviemates.R
import com.example.moviemates.USER_EMAIL
import com.example.moviemates.USER_ID
import com.example.moviemates.movieModels.EventModel
import com.example.moviemates.movieModels.friendModel
import com.google.firebase.firestore.FirebaseFirestore



class UserAdapter(private val userList: List<friendModel>) : RecyclerView.Adapter<UserAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val events = userList[position]
       holder.userEmailAddress.text = events.friendEmail
        isUserAlreadyFriend(events.friendId,events.userid) { isAlreadyFriend ->
            if (isAlreadyFriend) {
                holder.friendBtn.isEnabled = false
                holder.friendBtn.text = "Already Friend"
            }
            else{
                holder.friendBtn.isEnabled = true
            }
        }
       holder.friendBtn.setOnClickListener{
           val context = holder.itemView.context
           Toast.makeText(context, " $position,$events.friendEmail,${events.friendId}", Toast.LENGTH_SHORT).show()
           val userCollection = FirebaseFirestore.getInstance().collection("friends")
           val commentData = hashMapOf(
               "friendEmail" to events.friendEmail,
               "friendId" to events.friendId,
               "userid" to events.userid
           )


           userCollection.add(commentData)
               .addOnSuccessListener { documentReference ->
                   Toast.makeText(context, "Successfully made a friendship with ${events.friendEmail}", Toast.LENGTH_SHORT).show()
                   val intent = Intent(context, Dashboard::class.java)
                   intent.putExtra(USER_ID, events.userid)
                   intent.putExtra(USER_EMAIL, events.myemail)
                   context.startActivity(intent)
               }
               .addOnFailureListener { e ->
                   Toast.makeText(context, "${e.toString()}", Toast.LENGTH_SHORT).show()
               }




       }

    }

    public fun isUserAlreadyFriend(friendId: String,userid: String, callback: (Boolean) -> Unit) {
        val friendsCollection = FirebaseFirestore.getInstance().collection("friends")
        val query = friendsCollection.whereEqualTo("userid", userid).whereEqualTo("friendId", friendId)

        query.get().addOnSuccessListener { result ->
            callback(!result.isEmpty)
        }.addOnFailureListener { e ->
            // Handle failure
            callback(false)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userEmailAddress: TextView = itemView.findViewById(R.id.usernameTextView)
        val friendBtn: TextView = itemView.findViewById(R.id.addfriend)

    }
}
