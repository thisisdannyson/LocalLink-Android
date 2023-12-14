package com.example.locallink

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val list: MutableList<User>,
                  private val fragment: Fragment,
                  private val sharedPreferences: SharedPreferences,
                  private val editor: SharedPreferences.Editor): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var connectButton: Button
        var viewMoreButton: Button
        var bioText: TextView
        var majorText: TextView
        var name: TextView

        init {
            connectButton = itemView.findViewById(R.id.user_connect_button)
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
        if (text.length >= 38) {
            text = text.substring(0,38) + "..."
        }
        holder.bioText.text = text
        holder.majorText.text = list[position].major
        holder.name.text = list[position].name
        holder.viewMoreButton.setOnClickListener {
           displayPopUp(position)
        }
        holder.connectButton.isClickable = true
        if (sharedPreferences.contains(list[position].userId)) {
            holder.connectButton.text = "Added"
            holder.connectButton.alpha = 0.5f;
            holder.connectButton.isClickable = false
        }
        holder.connectButton.setOnClickListener {
            editor.putBoolean(list[position].userId, true)
            editor.commit()
            holder.connectButton.text = "Added"
            holder.connectButton.isClickable = false
            holder.connectButton.alpha = 0.5f
        }

    }

    private fun displayPopUp(position: Int) {
        val builder = AlertDialog.Builder(fragment.context)
        val dialog = builder.create()
        val view: View = fragment.layoutInflater.inflate(R.layout.user_pop_up, null)
        val nameText: TextView = view.findViewById(R.id.user_popup_name)
        val cancelIcon: ImageView = view.findViewById(R.id.user_popup_cancel_icon)
        val pronounText: TextView = view.findViewById(R.id.user_popup_pronoun_body)
        val locationText: TextView = view.findViewById(R.id.user_popup_location_body)
        val majorText: TextView = view.findViewById(R.id.user_popup_major_body)
        val classesText: TextView = view.findViewById(R.id.user_popup_classes_body)
        val interestsText: TextView = view.findViewById(R.id.user_popup_interests_body)
        val bioText: TextView = view.findViewById(R.id.user_popup_bio_body)


        nameText.text = list[position].name
        pronounText.text = list[position].pronouns
        locationText.text = list[position].building
        majorText.text = list[position].major
        classesText.text = list[position].classes
        interestsText.text = list[position].interests
        bioText.text = list[position].bio

        cancelIcon.setOnClickListener {
            dialog.dismiss()
        }


        dialog.setView(view)
        dialog.show()
    }
}