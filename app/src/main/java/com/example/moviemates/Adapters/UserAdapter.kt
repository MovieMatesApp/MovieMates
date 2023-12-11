import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.moviemates.R
import com.example.moviemates.movieModels.EventModel

class UserAdapter(private val eventList: List<EventModel>) : RecyclerView.Adapter<UserAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activty_item_events, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val events = eventList[position]
        holder.eventTextView.text= events.comment
        holder.eventDate.text =events.eventDate
        holder.userData.text =events.userEmail
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eventTextView: TextView = itemView.findViewById(R.id.eventDisplayText)
        val eventDate: TextView = itemView.findViewById(R.id.eventDateTextView)
        val userData:TextView = itemView.findViewById(R.id.userInfoTextView)
    }
}
