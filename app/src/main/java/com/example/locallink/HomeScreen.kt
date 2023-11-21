package com.example.locallink

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.locallink.databinding.FragmentHomeScreenBinding


class HomeScreen : Fragment() {
    private lateinit var binding: FragmentHomeScreenBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentHomeScreenBinding.inflate(layoutInflater)
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()

        //editor.clear().commit() // -> only disable comment if u want to clear all preferences for testing purposes
        (activity as AppCompatActivity?)!!.supportActionBar?.hide()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home_screen, container, false)
        val signButton: Button = view.findViewById(R.id.sign_in)
        val joinCommunityButton: Button = view.findViewById(R.id.join_button)
        signButton.setOnClickListener {
            if (sharedPreferences.getBoolean("accountCreated", false)) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, MapScreen())
                    ?.commit()
            } else {
                createErrorDialog()
            }
        }

        joinCommunityButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, LoginSSO())
                ?.commit()
        }

        return view
    }

    private fun createErrorDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Account Not Found")
            setMessage("No Account with associated husky info found.")
            setPositiveButton("Ok") { _, _ ->

            }
            show()
        }
    }

}