package com.example.katalog_makeup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi view
        val etCari = findViewById<EditText>(R.id.etCari)
        val btnCari = findViewById<Button>(R.id.btnCari)
        val cardLipstik = findViewById<LinearLayout>(R.id.cardLipstik)
        val cardEyeshadow = findViewById<LinearLayout>(R.id.cardEyeshadow)
        val cardFoundation = findViewById<LinearLayout>(R.id.cardFoundation)
        val cardBlushOn = findViewById<LinearLayout>(R.id.cardBlushOn)

        // VALIDASI SEARCH
        btnCari.setOnClickListener {
            val keyword = etCari.text.toString()
            if (keyword.isEmpty()) {
                Toast.makeText(this, "Kolom pencarian tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Mencari: $keyword", Toast.LENGTH_SHORT).show()
            }
        }

        // INTENT: klik kartu Lipstik
        cardLipstik.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nama", "Lipstick")
            intent.putExtra("brand", "MAC")
            intent.putExtra("harga", "Rp 415.000")
            intent.putExtra("kategori", "Bibir")
            intent.putExtra("gambar", "lipstik")
            startActivity(intent)
        }

        // INTENT: klik kartu Eyeshadow
        cardEyeshadow.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nama", "Eyeshadow Palette")
            intent.putExtra("brand", "Maybelline")
            intent.putExtra("harga", "Rp 189.000")
            intent.putExtra("kategori", "Mata")
            intent.putExtra("gambar", "eyeshadow")
            startActivity(intent)
        }

        // INTENT: klik kartu Foundation
        cardFoundation.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nama", "Foundation Fit Me")
            intent.putExtra("brand", "NYX")
            intent.putExtra("harga", "Rp 125.000")
            intent.putExtra("kategori", "Wajah")
            intent.putExtra("gambar", "foundition")
            startActivity(intent)
        }

        // INTENT: klik kartu Blush On
        cardBlushOn.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nama", "Blush On Matte")
            intent.putExtra("brand", "Wardah")
            intent.putExtra("harga", "Rp 65.000")
            intent.putExtra("kategori", "Pipi")
            intent.putExtra("gambar", "blush")
            startActivity(intent)
        }
    }
}