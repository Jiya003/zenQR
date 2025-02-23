package com.example.qrcodegenerator

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

class ScanActivity : AppCompatActivity() {
    private lateinit var scanButton: Button
    private lateinit var scannedText: TextView

    // Key for saving scanned text
    private val SCANNED_TEXT_KEY = "scanned_text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        scanButton = findViewById(R.id.scanButton)
        scannedText = findViewById(R.id.scannedText)

        // Restore text if available
        scannedText.text = savedInstanceState?.getString(SCANNED_TEXT_KEY) ?: "Scan a QR code"

        scanButton.setOnClickListener {
            scanQRCode()
        }
    }

    private val qrScannerLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            scannedText.text = "Scanned Data: ${result.contents}"
        } else {
            scannedText.text = "No QR code scanned."
        }
    }

    private fun scanQRCode() {
        val options = ScanOptions().apply {
            setDesiredBarcodeFormats(ScanOptions.QR_CODE)
            setPrompt("Scan a QR Code")
            setCameraId(0)
            setBeepEnabled(false)
            setBarcodeImageEnabled(true)
        }
        qrScannerLauncher.launch(options)
    }

    // Save scanned text on configuration changes
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SCANNED_TEXT_KEY, scannedText.text.toString())
    }
}
