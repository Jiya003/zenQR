package com.example.qrcodegenerator

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.File
import java.io.FileOutputStream

class ImagetoQRActivity : AppCompatActivity() {

    private lateinit var imageToConvert: ImageView
    private lateinit var convertButton: Button
    private lateinit var shareButton: Button
    private lateinit var uploadImage: Button
    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null
    private var generatedQRBitmap: Bitmap? = null
    lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_imageto_qractivity)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imageToConvert = findViewById(R.id.ImageView_ImageToQR)
        convertButton = findViewById(R.id.Convert_ImageToQR)
        uploadImage = findViewById(R.id.uploadImageButton_ImageToQR)
        shareButton = findViewById(R.id.Share_Button_ImagetoQR)
        imageView = findViewById(R.id.ImageViewQR_ImageToQR)

        uploadImage.setOnClickListener {
            if (checkStoragePermission()) {
                openGallery()
            } else {
                requestStoragePermission()
            }
        }

        convertButton.setOnClickListener {
            selectedImageUri?.let { uri ->
                generatedQRBitmap = generateQRCode(uri.toString())
                imageView.setImageBitmap(generatedQRBitmap)
                imageView.visibility = View.VISIBLE
                shareButton.visibility = View.VISIBLE
            } ?: Toast.makeText(this, "Please select an image first!", Toast.LENGTH_SHORT).show()
        }

        shareButton.setOnClickListener {
            shareQRCode()
        }
    }

    // Open gallery to select an image
    private val imagePickerLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            if (uri != null) {
                selectedImageUri = uri
                imageToConvert.setImageURI(uri)
                imageToConvert.visibility = View.VISIBLE
            } else {
                Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openGallery() {
        imagePickerLauncher.launch("image/*")
    }

    // Request storage permission
    private fun requestStoragePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                100
            )
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                100
            )
        }
    }

    private fun checkStoragePermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_MEDIA_IMAGES
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    // Generate QR code from URI
    private fun generateQRCode(text: String): Bitmap? {
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                text, BarcodeFormat.QR_CODE, 500, 500
            )
            val barcodeEncoder = BarcodeEncoder()
            barcodeEncoder.createBitmap(bitMatrix)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    // Share generated QR code
    private fun shareQRCode() {
            if (generatedQRBitmap == null) {
                Toast.makeText(this, "No QR Code to share!", Toast.LENGTH_SHORT).show()
                return
            }

            try {
                val cachePath = File(cacheDir, "images")
                cachePath.mkdirs()
                val file = File(cachePath, "qr_code.png")
                val outputFile = FileOutputStream(file)

                // Use generatedQRBitmap instead of bitmap
                generatedQRBitmap!!.compress(Bitmap.CompressFormat.PNG, 100, outputFile)
                outputFile.flush()
                outputFile.close()

                val uri: Uri = FileProvider.getUriForFile(this, "$packageName.provider", file)

                val intent = Intent(Intent.ACTION_SEND).apply {
                    type = "image/png"
                    putExtra(Intent.EXTRA_STREAM, uri)
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

                startActivity(Intent.createChooser(intent, "Share QR Code"))

            } catch (e: Exception) {
                e.printStackTrace()
                Toast.makeText(this, "Failed to share QR Code", Toast.LENGTH_LONG).show()
            }
        }

    }



