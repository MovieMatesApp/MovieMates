import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.R
import com.google.firebase.auth.FirebaseUser

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private var users: List<FirebaseUser> = emptyList()

    fun setUsers(users: List<FirebaseUser>) {
        this.users = users
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val usernameTextView: TextView = itemView.findViewById(R.id.usernameTextView)

        fun bind(user: FirebaseUser) {
            usernameTextView.text = user.displayName
            // Add other user information as needed
        }
    }
}
