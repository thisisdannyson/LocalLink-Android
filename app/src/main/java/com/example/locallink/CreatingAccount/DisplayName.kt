package com.example.locallink.CreatingAccount

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.locallink.BottomNav
import com.example.locallink.HomeScreen
import com.example.locallink.R
import com.mapbox.geojson.Feature


class DisplayName : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)!!
        editor = sharedPreferences.edit()
        editor.clear().commit()
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
        val view =  inflater.inflate(R.layout.fragment_display_name, container, false)
        val nextButton: Button = view.findViewById(R.id.display_name_next_button)
        val nameEdit: EditText = view.findViewById(R.id.display_name_text_edit)

        //val nameSharedPreferences = sharedPreferences.getString("displayName", "")

        nameEdit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {

                // you can call or do what you want with your EditText here

                // yourEditText...
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isEmpty()) {
                    nextButton.visibility = View.INVISIBLE
                } else {
                    nextButton.visibility = View.VISIBLE
                }
            }
        })

        nextButton.setOnClickListener {
            val name = nameEdit.text.toString()
            editor.putString("profileName", name)
            editor.apply()
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, Biography())
                ?.commit()
        }
        return view

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