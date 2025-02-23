package com.example.qrcodegenerator

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.qrcodegenerator.MenuActivity
import com.example.qrcodegenerator.R

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure the activity uses edge-to-edge layout

        setContentView(R.layout.activity_main)

             // Start MenuActivity after a delay
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
            finish()  // Finish MainActivity so that the user can't navigate back to it
        }, 1500) // Delay in milliseconds
    }
}
