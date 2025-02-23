package com.example.qrcodegenerator

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class ContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact)

        val emailButton = findViewById<Button>(R.id.contactEmailButton)

        emailButton.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:divyanshimaurya611@gmail.com")  // Replace with actual email
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Support Request")
            startActivity(emailIntent)
        }
    }
}