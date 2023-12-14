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

class SelectProfilePhoto : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_select_profile_photo, container, false)
        val backButton: Button = view.findViewById(R.id.profilePhoto_back_button)
        val selectGalleryButton: Button = view.findViewById(R.id.profilePhoto_gallery_button)
        val takePhotoButton: Button = view.findViewById(R.id.profilePhoto_takePhoto_button)


        backButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, Biography())
                ?.commit()
        }

        selectGalleryButton.setOnClickListener {
            createGalleryDialog()
        }

        takePhotoButton.setOnClickListener {
            createTakePhotoDialog()
        }
        return view
    }

    private fun createGalleryDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Select Photo From Gallery")
            setMessage("At this point we would allow the user to select a photo from their gallery and have it shown," +
                    " but assume we selected a photo")
            setPositiveButton("continue") { _, _ ->
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, ProfilePhotoConfirm())
                    ?.commit()
            }
            show()
        }
    }

    private fun createTakePhotoDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Take Photo")
            setMessage("At this point we would allow the user to take a photo with their camera and have it shown," +
                    " but assume we took a picture")
            setPositiveButton("continue") { _, _ ->
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, ProfilePhotoConfirm())
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