package com.example.locallink

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatScreen(private val bottomNav: BottomNavigationView) : Fragment() {

    private lateinit var noStudentsText: TextView
    private lateinit var studentsAddedText: TextView
    private lateinit var studentsAddedInstructions: TextView
    private lateinit var lineDivider: View
    private lateinit var recyclerView: RecyclerView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNav.visibility = View.VISIBLE
        bottomNav.selectedItemId = R.id.menu_chat
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

        UserDatabase.generateUsersInSearch(sharedPreferences)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_chat_screen, container, false)
        noStudentsText = view.findViewById(R.id.chat_results_no_students_added)
        studentsAddedText = view.findViewById(R.id.chat_results_studentsAddedNumber)
        studentsAddedInstructions = view.findViewById(R.id.chat_results_studentsAddedInstructions)
        lineDivider = view.findViewById(R.id.chat_results_divider_line)
        recyclerView = view.findViewById(R.id.chat_results_recycler)

        val usersAddedAdapter = UserAddedAdapter(UserDatabase.usersAdded,
            this)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = usersAddedAdapter
        recyclerView.addItemDecoration(UserItemDecorator())

        if (UserDatabase.usersAdded.isEmpty()) {
            enableEmptyResultsText()

        } else {
            enableStudentsFoundText()
        }


        return view
    }

    private fun enableEmptyResultsText() {
        studentsAddedText.visibility = View.INVISIBLE
        studentsAddedInstructions.visibility = View.INVISIBLE
        lineDivider.visibility = View.INVISIBLE
        noStudentsText.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    private fun enableStudentsFoundText() {
        studentsAddedText.visibility = View.VISIBLE
        var numFoundText = ""
        numFoundText = if (UserDatabase.usersAdded.size == 1) {
            "1 Student Added!"
        } else {
            "${UserDatabase.usersAdded.size} Students Added!"
        }
        studentsAddedText.text = numFoundText
        studentsAddedInstructions.visibility = View.VISIBLE
        lineDivider.visibility = View.VISIBLE
        noStudentsText.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }
}

