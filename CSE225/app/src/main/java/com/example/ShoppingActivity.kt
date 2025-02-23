package com.example

import android.os.Bundle
import android.widget.RatingBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast

class ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_shopping)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val rating_bar1=findViewById<RatingBar>(R.id.dress_image_view_rating_bar)
        val rating_bar2=findViewById<RatingBar>(R.id.men_formal_image_view_rating_bar)
        val rating_bar3=findViewById<RatingBar>(R.id.child_dress_image_view_rating_bar)
        val rating_bar4=findViewById<RatingBar>(R.id.women_image_view_rating_bar)

        rating_bar1.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = rating
            Toast.makeText(this, "Rating changed of  $rating", Toast.LENGTH_SHORT).show()
        }

        rating_bar2.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = rating
            Toast.makeText(this, "Rating changed to $rating", Toast.LENGTH_SHORT).show()
        }

        rating_bar3.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = rating
            Toast.makeText(this, "Rating changed to $rating", Toast.LENGTH_SHORT).show()
        }

        rating_bar4.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingBar.rating = rating
            Toast.makeText(this, "Rating changed to $rating", Toast.LENGTH_SHORT).show()
        }


    }
}