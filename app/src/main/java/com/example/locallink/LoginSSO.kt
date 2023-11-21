package com.example.locallink

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.locallink.CreatingAccount.DisplayName


/**
 * A simple [Fragment] subclass.
 * Use the [LoginSSO.newInstance] factory method to
 * create an instance of this fragment.
 */
class LoginSSO : Fragment() {
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
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login_s_s_o, container, false)
        val signInButton: Button = view.findViewById(R.id.sso)
        signInButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, NortheasternSSO())
                ?.commit()
        }
        return view
    }

    private fun createMockDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("Sign in w/ SSO")
            setMessage("Normally this would send you to an external site and verify with backend logic." +
                    "\n\nAssume for now that we successfully signed in with SSO.")
            setPositiveButton("next") { _, _ ->
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, DisplayName())
                    ?.commit()
            }
            show()
        }
    }
}