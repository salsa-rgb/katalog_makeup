package com.example.katalog_makeup

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    // TAG Logcat menggunakan NIM
    val TAG = "42430040"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Log.d(TAG, "DetailActivity dibuka")

        try {
            // Inisialisasi view
            val tvNamaProduk = findViewById<TextView>(R.id.tvNamaProduk)
            val tvBrand = findViewById<TextView>(R.id.tvBrand)
            val tvHarga = findViewById<TextView>(R.id.tvHarga)
            val tvKategoriDetail = findViewById<TextView>(R.id.tvKategoriDetail)
            val imgProduk = findViewById<ImageView>(R.id.imgProduk)
            val btnKembali = findViewById<Button>(R.id.btnKembali)

            // Ambil data dari Intent
            val nama = intent.getStringExtra("nama") ?: ""
            val brand = intent.getStringExtra("brand") ?: ""
            val harga = intent.getStringExtra("harga") ?: ""
            val kategori = intent.getStringExtra("kategori") ?: ""
            val gambar = intent.getStringExtra("gambar") ?: ""

            Log.d(TAG, "Data diterima - Nama: $nama, Brand: $brand, Harga: $harga")

            // Tampilkan data ke view
            tvNamaProduk.text = nama
            tvBrand.text = "Brand: $brand"
            tvHarga.text = "Harga: $harga"
            tvKategoriDetail.text = "Kategori: $kategori"

            // Set gambar sesuai produk
            when (gambar) {
                "lipstik" -> imgProduk.setImageResource(R.drawable.lipstik)
                "eyeshadow" -> imgProduk.setImageResource(R.drawable.eyeshadow)
                "foundition" -> imgProduk.setImageResource(R.drawable.foundition)
                "blush" -> imgProduk.setImageResource(R.drawable.blush)
            }

            Log.d(TAG, "Detail produk berhasil ditampilkan: $nama")

            // Tombol kembali
            btnKembali.setOnClickListener {
                Log.d(TAG, "Tombol kembali diklik, kembali ke MainActivity")
                finish()
            }

        } catch (e: Exception) {
            Log.e(TAG, "Error di DetailActivity: ${e.message}")
            Toast.makeText(this, "Gagal memuat detail produk", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}