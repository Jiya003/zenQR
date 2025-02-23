package com.example.qrcodegenerator

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class FeedBackActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_feed_back)


        val b:Button=findViewById(R.id.submitFeedbackButton)
        val t:EditText=findViewById(R.id.feedbackInput)
        b.setOnClickListener{
           if(t.text.toString().isNotEmpty()){
               Toast.makeText(this,"Feedback Submitted",Toast.LENGTH_SHORT).show()
           }else{
               Toast.makeText(this,"Please Enter feedback",Toast.LENGTH_LONG).show()
           }
        }
    }
}