package com.example.locallink

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.locallink.CreatingAccount.DisplayName


/**
 * A simple [Fragment] subclass.
 * Use the [ChatUserScreen.newInstance] factory method to
 * create an instance of this fragment.
 */
class ChatUserScreen(private val user: User) : Fragment() {

    private lateinit var userName: TextView
    private lateinit var backButton: ImageView
    private lateinit var sendButton: ImageView
    private lateinit var messageEditText: EditText

    private lateinit var text3: TextView
    private lateinit var text4: TextView
    private lateinit var text5: TextView
    private lateinit var text6: TextView
    private lateinit var text7: TextView
    private lateinit var text8: TextView
    private var index = 1

    private var indexToMessageMap: MutableMap<Int, TextView> = mutableMapOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_chat_user_screen, container, false)
        userName = view.findViewById(R.id.chat_user_name)
        backButton = view.findViewById(R.id.chat_user_back_icon)
        sendButton = view.findViewById(R.id.chat_user_send_button)
        messageEditText = view.findViewById(R.id.chat_user_edit_text)

        text3 = view.findViewById(R.id.chat_user_pre_text_3)
        text4 = view.findViewById(R.id.chat_user_pre_text_4)
        text5 = view.findViewById(R.id.chat_user_pre_text_5)
        text6 = view.findViewById(R.id.chat_user_pre_text_6)
        text7 = view.findViewById(R.id.chat_user_pre_text_7)
        text8 = view.findViewById(R.id.chat_user_pre_text_8)

        indexToMessageMap.apply {
            put(1, text3)
            put(2, text4)
            put(3, text5)
            put(4, text6)
            put(5, text7)
            put(6, text8)
        }


        userName.text = user.name
        backButton.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.nav_container, ChatScreen(BottomNav.bottomNav))
                ?.commit()
        }

        sendButton.setOnClickListener {

            val message = messageEditText.text.toString()
            if (message.isNotEmpty()) {
                indexToMessageMap[index]?.visibility = View.VISIBLE
                indexToMessageMap[index]?.text = message

                index += 1
                messageEditText.text.clear()
            }
        }
        return view
    }

}