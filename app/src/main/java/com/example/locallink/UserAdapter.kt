package com.example.locallink

import android.app.AlertDialog
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val list: MutableList<User>,
                  private val fragment: Fragment,
                  private val sharedPreferences: SharedPreferences,
                  private val editor: SharedPreferences.Editor): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chatButton: Button
        var viewMoreButton: Button
        var bioText: TextView
        var majorText: TextView
        var name: TextView

        init {
            chatButton = itemView.findViewById(R.id.user_chat_button)
            viewMoreButton = itemView.findViewById(R.id.user_view_more_button)
            bioText = itemView.findViewById(R.id.user_bio_body)
            majorText = itemView.findViewById(R.id.user_major_body)
            name = itemView.findViewById(R.id.user_name)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row_item, parent,false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        var text = list[position].bio
        if (text.length >= 39) {
            text = text.substring(0,39) + "..."
        }
        holder.bioText.text = text
        holder.majorText.text = list[position].major
        holder.name.text = list[position].name
        holder.viewMoreButton.setOnClickListener {
           // displayPopUp(holder, position)
            Log.i("adapter", "clicking on view more with name: ${list[position].name}")
        }

    }

    private fun displayPopUp(holder: UserViewHolder, position: Int) {
        val builder = AlertDialog.Builder(fragment.context)
        builder.apply {
            val customLayout: View = fragment.layoutInflater.inflate(R.layout.building_popup, null)
            setView(customLayout)
        }
        builder.show()
    }
}