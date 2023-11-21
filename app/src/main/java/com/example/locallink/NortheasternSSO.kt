package com.example.locallink

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.locallink.CreatingAccount.DisplayName


class NortheasternSSO : Fragment() {

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
        val view = inflater.inflate(R.layout.fragment_northeastern_s_s_o, container, false)
        val emailText: EditText = view.findViewById(R.id.northeastern_sso_email_text)
        val passwordText: EditText = view.findViewById(R.id.northeastern_sso_password_text)
        val signOnButton: Button = view.findViewById(R.id.northeastern_sso_signin_button)
        signOnButton.setOnClickListener {
            if (checkUserInputs(emailText.text.toString(), passwordText.text.toString())) {
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.nav_container, DisplayName())
                    ?.commit()
            } else {
                displayInvalidInput()
            }
        }

        val forgotPassword: TextView = view.findViewById(R.id.northeastern_sso_forgot_password)
        forgotPassword.movementMethod = LinkMovementMethod.getInstance()
        forgotPassword.setLinkTextColor(Color.BLUE)

        val visitTechServiceText: TextView = view.findViewById(R.id.northeastern_sso_tech_service_hyperlink)
        visitTechServiceText.movementMethod = LinkMovementMethod.getInstance()
        visitTechServiceText.setLinkTextColor(Color.BLUE)

        return view
    }

    private fun checkUserInputs(emailText: String, passwordText: String): Boolean {
        return emailText.endsWith("@northeastern.edu") && passwordText.length >= 8
    }

    private fun displayInvalidInput() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.apply {
            setTitle("User Input Invalid")
            setMessage("Please enter your northeastern email ending in @northeastern.edu " +
                    "\nand that your password is at least 8 characters long.")
            setPositiveButton("ok") { _, _ ->

            }
            show()
        }
    }
}