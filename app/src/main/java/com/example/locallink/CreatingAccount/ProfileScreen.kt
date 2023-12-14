package com.example.locallink.CreatingAccount

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.locallink.BottomNav
import com.example.locallink.HomeScreen
import com.example.locallink.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.w3c.dom.Text

class ProfileScreen(private val bottomNav: BottomNavigationView) : Fragment() {
    private lateinit var name: TextView
    private lateinit var location: TextView
    private lateinit var major: TextView
    private lateinit var pronoun: TextView
    private lateinit var classes: TextView
    private lateinit var interests: TextView
    private lateinit var bio: TextView
    private lateinit var logoutButton: Button

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNav.visibility = View.VISIBLE
        bottomNav.selectedItemId = R.id.menu_profile
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile_screen, container, false)
        name = view.findViewById(R.id.profile_name)
        location = view.findViewById(R.id.profile_location_body)
        major = view.findViewById(R.id.profile_major_body)
        classes = view.findViewById(R.id.profile_classes_body)
        pronoun = view.findViewById(R.id.profile_pronoun_body)
        interests = view.findViewById(R.id.profile_interests_body)
        bio = view.findViewById(R.id.profile_bio_body)
        logoutButton = view.findViewById(R.id.profile_logout_button)

        name.text = sharedPreferences.getString("profileName", null)
        location.text = sharedPreferences.getString("profileLocation", null)
        major.text = sharedPreferences.getString("profileMajor", null)
        classes.text = sharedPreferences.getString("profileClasses", null)
        interests.text = sharedPreferences.getString("profileInterests", null)
        bio.text = sharedPreferences.getString("profileBio", null)
        pronoun.text = sharedPreferences.getString("profilePronouns", null)

        logoutButton.setOnClickListener {
            displayConfirmLogOut()
        }
        return view
    }

    private fun displayConfirmLogOut() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Logging out")
            setMessage("${name.text}, are you sure you want to logout?")
            setNegativeButton("no") {_, _ ->

            }
            setPositiveButton("yes") { _, _ ->
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, HomeScreen(BottomNav.bottomNav))
                    ?.commit()
            }
            show()
        }
    }
}