package com.ok.chatwithfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ResultReceiver
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.lang.ref.Reference

class ChatActivity : AppCompatActivity() {

    private lateinit var RecyclerView: RecyclerView
    private lateinit var message_box: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var dbReference: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        dbReference = FirebaseDatabase.getInstance().getReference()
        receiverRoom = senderUid + receiverUid
        senderRoom = receiverUid + senderUid
        supportActionBar?.title = name

        RecyclerView = findViewById(R.id.RecyclerView)
        message_box = findViewById(R.id.messageBox)
        sendButton = findViewById(R.id.send_button)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        sendButton.setOnClickListener(){

            val message = message_box.text.toString()
            val messageObjects = Message(message,senderUid)

            dbReference.child("chat").child(senderRoom!!).child("message").push()
                .setValue(messageObjects)

        }
    }
}