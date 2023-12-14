package com.example.locallink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView

class SearchResults : Fragment() {

    private lateinit var backToMapButton: Button
    private lateinit var noStudentsText: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_search_results, container, false)
        backToMapButton = view.findViewById(R.id.search_results_back_to_map)
        noStudentsText = view.findViewById(R.id.search_results_no_students_found)


        backToMapButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, MapScreen())
                ?.commit()
        }

        if (UserDatabase.localUserMapToBuilding.isEmpty()) {
            noStudentsText.visibility = View.VISIBLE
        } else {
            noStudentsText.visibility = View.INVISIBLE
        }


        return view
    }

}