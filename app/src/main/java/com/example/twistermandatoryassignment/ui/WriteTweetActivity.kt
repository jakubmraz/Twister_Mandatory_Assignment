package com.example.twistermandatoryassignment.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.twistermandatoryassignment.R
import com.example.twistermandatoryassignment.classes.Message
import com.example.twistermandatoryassignment.webapi.WebRequests
import com.google.firebase.auth.FirebaseAuth

class WriteTweetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_write_tweet)
        val backButton = findViewById<Button>(R.id.buttonWriteBack)
        backButton.setOnClickListener { switchBackToMainActivity() }
        val sendTweetButton = findViewById<Button>(R.id.buttonWriteSend)
        sendTweetButton.setOnClickListener { sendTweet() }
    }

    private fun sendTweet() {
        val contentEditText = findViewById<EditText>(R.id.editTextWriteContent)
        val content = contentEditText.text.toString()
        val mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser
        val email = currentUser.email
        val message = Message(content, email)
        val webRequests = WebRequests()
        webRequests.uploadMessage(message)
        switchBackToMainActivity()
    }

    private fun switchBackToMainActivity() {
        val intentBack = Intent()
        setResult(Activity.RESULT_OK, intentBack)
        finish()
    }
}