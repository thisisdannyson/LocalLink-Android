package com.example.locallink

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchResults : Fragment() {

    private lateinit var backToMapButton: Button
    private lateinit var noStudentsText: TextView
    private lateinit var studentsFoundText: TextView
    private lateinit var studentsFoundInstructions: TextView
    private lateinit var lineDivider: View
    private lateinit var recyclerView: RecyclerView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_search_results, container, false)
        backToMapButton = view.findViewById(R.id.search_results_back_to_map)
        noStudentsText = view.findViewById(R.id.search_results_no_students_found)
        studentsFoundText = view.findViewById(R.id.search_results_studentsFoundNumber)
        studentsFoundInstructions = view.findViewById(R.id.search_results_studentsFoundInstructions)
        lineDivider = view.findViewById(R.id.search_results_divider_line)
        recyclerView = view.findViewById(R.id.search_results_recycler)

        backToMapButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, MapScreen())
                ?.commit()
        }

        val usersAdapter = UserAdapter(UserDatabase.usersFoundinSearch,
            this,
            sharedPreferences,
            editor)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = usersAdapter
        recyclerView.addItemDecoration(UserItemDecorator())


        if (UserDatabase.localUserMapToBuilding.isEmpty()) {
            enableEmptyResultsText()

        } else {
            enableStudentsFoundText()
        }


        return view
    }

    private fun enableEmptyResultsText() {
        studentsFoundText.visibility = View.INVISIBLE
        studentsFoundInstructions.visibility = View.INVISIBLE
        lineDivider.visibility = View.INVISIBLE
        noStudentsText.visibility = View.VISIBLE
        recyclerView.visibility = View.INVISIBLE
    }

    private fun enableStudentsFoundText() {
        studentsFoundText.visibility = View.VISIBLE
        var numFoundText = ""
        numFoundText = if (UserDatabase.numUsersFound == 1) {
            "1 Student Found!"
        } else {
            "${UserDatabase.numUsersFound} Students Found!"
        }
        studentsFoundText.text = numFoundText
        studentsFoundInstructions.visibility = View.VISIBLE
        lineDivider.visibility = View.VISIBLE
        noStudentsText.visibility = View.INVISIBLE
        recyclerView.visibility = View.VISIBLE
    }

}

class UserItemDecorator(): RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.right = 20
        outRect.left = 20
        outRect.bottom = 20
        outRect.top = 20
    }
}