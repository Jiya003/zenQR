package com.example.qrcodegenerator

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream

class TexttoQRActivity : AppCompatActivity() {
    lateinit var EditText_text: EditText
    lateinit var Convert_Button: Button
    lateinit var ImageView_QR: ImageView
    lateinit var Share_Button: Button

    lateinit var bitmap: Bitmap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_textto_qractivity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        EditText_text = findViewById(R.id.EditText_TexttoQR)
        Convert_Button = findViewById(R.id.Convert_TextToQR)
        ImageView_QR = findViewById(R.id.ImageView_TextToQR)
        Share_Button = findViewById(R.id.Share_Button_TexttoQR)

        Convert_Button.setOnClickListener {
            val text = EditText_text.text.toString().trim()
            if (text.isNotEmpty()) {
                bitmap = QRGenerator(text)
                ImageView_QR.setImageBitmap(bitmap)
                Share_Button.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "Please enter text to convert", Toast.LENGTH_LONG).show()
            }
        }

        Share_Button.setOnClickListener {
            shareQRCode()
        }
        val toolbar:Toolbar=findViewById(R.id.Text_ToQR_Toolbar)
        setSupportActionBar(toolbar)



    }

    private fun QRGenerator(text: String): Bitmap {
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                text, BarcodeFormat.QR_CODE, 500, 500
            )
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: Exception) {
            e.printStackTrace()
            Bitmap.createBitmap(500, 500, Bitmap.Config.ARGB_8888) // Returns a blank bitmap in case of failure
        }
    }

    private fun shareQRCode(){
        try{
            val cachePath= File(cacheDir,"images")
            cachePath.mkdirs()
            val file=File(cachePath,"qr_code.png")
            val outputFile=FileOutputStream(file)

            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputFile)
            outputFile.flush()
            outputFile.close()
            val uri: Uri = FileProvider.getUriForFile(this,"$packageName.provider",file)

            val intent=Intent(Intent.ACTION_SEND).apply{
                type="image/*"
                putExtra(Intent.EXTRA_STREAM,uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }

            startActivity(Intent.createChooser(intent,"Share QR Code"))

        }catch (e: Exception){
            e.printStackTrace()
            Toast.makeText(this,"Failed to share QR Code", Toast.LENGTH_LONG).show()
        }
    }


    private fun showExitDialog(){
        val dialogView= LayoutInflater.from(this).inflate(R.layout.custom_popup,null)
        val builder= AlertDialog.Builder(this)
        builder.setView(dialogView)

        val alertDialog=builder.create()
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun showPopupMenu(view: View){
        val popup= PopupMenu(this,view)
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

}
