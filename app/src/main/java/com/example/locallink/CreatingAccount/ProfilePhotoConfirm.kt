package com.example.locallink.CreatingAccount

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.locallink.BottomNav
import com.example.locallink.HomeScreen
import com.example.locallink.R

class ProfilePhotoConfirm : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.account_create_menu, menu)
        val supportActionBar = (activity as AppCompatActivity?)!!.supportActionBar!!
        supportActionBar.show()
        supportActionBar.title = "Creating an Account"
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.item1) {
            createConfirmCancelDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profile_photo_confirm, container, false)
        val retakeButton: Button = view.findViewById(R.id.profilePhoto_confirm_retake_button)
        val confirmButton: Button = view.findViewById(R.id.profilePhoto_confirm_button)

        retakeButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, SelectProfilePhoto())
                ?.commit()
        }

        confirmButton.setOnClickListener {
            editor.putBoolean("accountCreated", true)
            editor.apply()
            displaySuccessDialog()
        }

        return view
    }

    private fun displaySuccessDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Account Created")
            setMessage("Yay! Your account has been created and you may now continue on your search" +
                    " to connect with Northeastern Students!")
            setPositiveButton("back to homepage") { _, _ ->
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, HomeScreen(BottomNav.bottomNav))
                    ?.commit()
            }
            show()
        }
    }

    private fun createConfirmCancelDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Cancel Account Creation")
            setMessage("Are you sure you want to cancel creating an account?")
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