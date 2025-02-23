package com.example.qrcodegenerator

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.cardview.widget.CardView
import com.facebook.shimmer.ShimmerFrameLayout

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        val actualContent = findViewById<android.widget.ScrollView>(R.id.actual_content)
        val toolbar: Toolbar =findViewById(R.id.Menu_Toolbar)
        setSupportActionBar(toolbar)


        // Initialize the shimmer effect
        val shimmerViewContainer: ShimmerFrameLayout = findViewById(R.id.shimmer_view_container)
        shimmerViewContainer.startShimmer()

        // Simulate a delay to mimic data loading
        Handler(Looper.getMainLooper()).postDelayed({
            // Stop the shimmer effect
            shimmerViewContainer.stopShimmer()
            shimmerViewContainer.visibility = android.view.View.GONE

            // Show the actual content
            actualContent.visibility = android.view.View.VISIBLE
        }, 3000) // 3 seconds delay

        val cardView1:CardView=findViewById(R.id.cardview_text_qr)
        val cardView2:CardView=findViewById(R.id.cardview_image_qr)


        cardView1.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, TexttoQRActivity::class.java)
                startActivity(intent)
            }
        )

        cardView2.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, ImagetoQRActivity::class.java)
                startActivity(intent)
            }
        )




        onBackPressedDispatcher.addCallback(this,object : OnBackPressedCallback(true){
            override fun handleOnBackPressed(){
                showExitDialogBox()
            }
        })

        val scanActivity:Button=findViewById(R.id.scanButton)
        scanActivity.setOnClickListener(
            View.OnClickListener {
                val intent = Intent(this, ScanActivity::class.java)
                startActivity(intent)
            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.action_menu_item -> {
                showPopupMenu(findViewById(R.id.action_menu_item))
                true
            }
            else -> super.onOptionsItemSelected(item)
    }
    }

    private fun showPopupMenu(view: View){
        val popup=PopupMenu(this,view)
        popup.menuInflater.inflate(R.menu.main_menu,popup.menu)

        popup.menu.removeItem(R.id.action_menu_item)

        popup.setOnMenuItemClickListener {
            menuItem ->
            when(menuItem.itemId) {
                R.id.feedback_item -> {
                    val intent = Intent(this, FeedBackActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.contactMe_item -> {
                    val intent = Intent(this, ContactActivity::class.java)
                    startActivity(intent)
                    true
                }
                else-> false
            }
        }
        popup.show()
    }

    private fun showExitDialogBox(){
        val dialogView= LayoutInflater.from(this).inflate(R.layout.custom_popup,null)
        val builder= AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog=builder.create()

        val btnYes=dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo=dialogView.findViewById<Button>(R.id.btnNo)

        btnNo.setOnClickListener {
            alertDialog.dismiss()
        }

        btnYes.setOnClickListener{
            alertDialog.dismiss()
            finishAffinity()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()


    }
}