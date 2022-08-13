package com.ok.chatwithfriends

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.jar.Attributes

/**
 * nothing
 */
class SignUp : AppCompatActivity() {
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var name: EditText
    private lateinit var button2: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var dbReferance: DatabaseReference


    /**
     *
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        name = findViewById(R.id.name)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        button2 = findViewById(R.id.button2)

        button2.setOnClickListener {
            val userName = name.text.toString()
            val email = email.text.toString()
            val password = password.text.toString()

            signUp(userName, email, password)
        }
    }

    private fun signUp(name: String, email: String, password: String) {
        //login User
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String?) {

        dbReferance = FirebaseDatabase.getInstance().getReference()
        dbReferance.child("user").child(uid!!).setValue(User(name, email, uid))
    }
}