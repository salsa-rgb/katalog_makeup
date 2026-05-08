package com.example.katalog_makeup

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // TAG Logcat menggunakan NIM
    val TAG = "42430040"

    // =============================================
    // DATA ARRAY PRODUK
    // =============================================
    val namaProduk = arrayOf("Lipstick Ruby Woo", "Eyeshadow Palette", "Foundation Fit Me", "Blush On Matte")
    val brandProduk = arrayOf("MAC", "Maybelline", "NYX", "Wardah")
    val hargaProduk = arrayOf("Rp 415.000", "Rp 189.000", "Rp 125.000", "Rp 65.000")
    val kategoriProduk = arrayOf("Bibir", "Mata", "Wajah", "Pipi")
    val gambarProduk = arrayOf("lipstik", "eyeshadow", "foundition", "blush")

    // Array urutan produk terlaris
    val urутanTerlaris = arrayOf(0, 3, 2, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "MainActivity dibuka")

        // Inisialisasi view
        val etCari = findViewById<EditText>(R.id.etCari)
        val btnCari = findViewById<Button>(R.id.btnCari)
        val btnSortAZ = findViewById<Button>(R.id.btnSortAZ)
        val btnTerlaris = findViewById<Button>(R.id.btnTerlaris)

        // Tampilkan semua produk pertama kali
        try {
            tampilkanProduk(arrayOf(0, 1, 2, 3))
            Log.d(TAG, "Semua produk berhasil ditampilkan")
        } catch (e: Exception) {
            Log.e(TAG, "Error saat menampilkan produk: ${e.message}")
            Toast.makeText(this, "Gagal menampilkan produk", Toast.LENGTH_SHORT).show()
        }

        // =============================================
        // VALIDASI + LINEAR SEARCH
        // =============================================
        btnCari.setOnClickListener {
            try {
                val keyword = etCari.text.toString().trim()
                Log.d(TAG, "Mencari produk: $keyword")

                if (keyword.isEmpty()) {
                    Log.d(TAG, "Pencarian gagal - kolom kosong")
                    Toast.makeText(this, "Kolom pencarian tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                } else {
                    val hasilSearch = linearSearch(keyword)
                    if (hasilSearch.isEmpty()) {
                        Log.d(TAG, "Produk '$keyword' tidak ditemukan")
                        Toast.makeText(this, "Produk '$keyword' tidak ditemukan!", Toast.LENGTH_SHORT).show()
                    } else {
                        tampilkanProduk(hasilSearch.toTypedArray())
                        Log.d(TAG, "Ditemukan ${hasilSearch.size} produk untuk keyword '$keyword'")
                        Toast.makeText(this, "${hasilSearch.size} produk ditemukan", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error saat pencarian: ${e.message}")
                Toast.makeText(this, "Terjadi kesalahan saat mencari", Toast.LENGTH_SHORT).show()
            }
        }

        // =============================================
        // BUBBLE SORT A-Z
        // =============================================
        btnSortAZ.setOnClickListener {
            try {
                val sorted = bubbleSort(ascending = true)
                tampilkanProduk(sorted)
                Log.d(TAG, "Produk diurutkan A-Z")
                Toast.makeText(this, "Diurutkan A-Z", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e(TAG, "Error saat sorting A-Z: ${e.message}")
                Toast.makeText(this, "Terjadi kesalahan saat mengurutkan", Toast.LENGTH_SHORT).show()
            }
        }

        // =============================================
        // PRODUK TERLARIS
        // =============================================
        btnTerlaris.setOnClickListener {
            try {
                tampilkanProduk(urутanTerlaris)
                Log.d(TAG, "Menampilkan produk terlaris")
                Toast.makeText(this, "Menampilkan produk terlaris", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Log.e(TAG, "Error saat menampilkan terlaris: ${e.message}")
                Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // =============================================
    // FUNGSI LINEAR SEARCH
    // =============================================
    fun linearSearch(keyword: String): List<Int> {
        val hasil = mutableListOf<Int>()
        for (i in namaProduk.indices) {
            if (namaProduk[i].lowercase().contains(keyword.lowercase())) {
                hasil.add(i)
            }
        }
        return hasil
    }

    // =============================================
    // FUNGSI BUBBLE SORT
    // =============================================
    fun bubbleSort(ascending: Boolean): Array<Int> {
        val index = arrayOf(0, 1, 2, 3)
        for (i in 0 until index.size - 1) {
            for (j in 0 until index.size - 1 - i) {
                val a = namaProduk[index[j]]
                val b = namaProduk[index[j + 1]]
                val perluTukar = if (ascending) a > b else a < b
                if (perluTukar) {
                    val temp = index[j]
                    index[j] = index[j + 1]
                    index[j + 1] = temp
                }
            }
        }
        return index
    }

    // =============================================
    // FUNGSI TAMPILKAN PRODUK
    // =============================================
    fun tampilkanProduk(indexList: Array<Int>) {
        val container = findViewById<LinearLayout>(R.id.containerProduk)
        container.removeAllViews()

        var i = 0
        while (i < indexList.size) {
            val baris = LinearLayout(this)
            baris.orientation = LinearLayout.HORIZONTAL
            val paramBaris = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            baris.layoutParams = paramBaris

            baris.addView(buatKartu(indexList[i]))

            if (i + 1 < indexList.size) {
                baris.addView(buatKartu(indexList[i + 1]))
            } else {
                val kosong = LinearLayout(this)
                val paramKosong = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
                kosong.layoutParams = paramKosong
                baris.addView(kosong)
            }

            container.addView(baris)
            i += 2
        }
    }

    // =============================================
    // FUNGSI BUAT KARTU PRODUK
    // =============================================
    fun buatKartu(index: Int): LinearLayout {
        val kartu = LinearLayout(this)
        kartu.orientation = LinearLayout.VERTICAL
        kartu.setBackgroundResource(R.drawable.bg_card)
        kartu.isClickable = true
        kartu.isFocusable = true

        val paramKartu = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        paramKartu.setMargins(8, 8, 8, 8)
        kartu.layoutParams = paramKartu

        // Gambar produk
        val imgView = ImageView(this)
        val paramImg = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT, 250
        )
        imgView.layoutParams = paramImg
        imgView.scaleType = ImageView.ScaleType.CENTER_CROP
        when (gambarProduk[index]) {
            "lipstik" -> imgView.setImageResource(R.drawable.lipstik)
            "eyeshadow" -> imgView.setImageResource(R.drawable.eyeshadow)
            "foundition" -> imgView.setImageResource(R.drawable.foundition)
            "blush" -> imgView.setImageResource(R.drawable.blush)
        }
        kartu.addView(imgView)

        // Info produk
        val infoLayout = LinearLayout(this)
        infoLayout.orientation = LinearLayout.VERTICAL
        val paramInfo = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        paramInfo.setMargins(20, 16, 20, 20)
        infoLayout.layoutParams = paramInfo

        val tvBrand = TextView(this)
        tvBrand.text = brandProduk[index]
        tvBrand.textSize = 10f
        tvBrand.setTextColor(android.graphics.Color.parseColor("#BB0077"))
        infoLayout.addView(tvBrand)

        val tvNama = TextView(this)
        tvNama.text = namaProduk[index]
        tvNama.textSize = 13f
        tvNama.setTextColor(android.graphics.Color.parseColor("#333333"))
        tvNama.setTypeface(null, android.graphics.Typeface.BOLD)
        infoLayout.addView(tvNama)

        val tvHarga = TextView(this)
        tvHarga.text = hargaProduk[index]
        tvHarga.textSize = 12f
        tvHarga.setTextColor(android.graphics.Color.parseColor("#D48FA0"))
        tvHarga.setTypeface(null, android.graphics.Typeface.BOLD)
        infoLayout.addView(tvHarga)

        kartu.addView(infoLayout)

        // Klik kartu pindah ke detail
        kartu.setOnClickListener {
            try {
                Log.d(TAG, "Kartu diklik: ${namaProduk[index]}")
                val intent = Intent(this, DetailActivity::class.java)
                intent.putExtra("nama", namaProduk[index])
                intent.putExtra("brand", brandProduk[index])
                intent.putExtra("harga", hargaProduk[index])
                intent.putExtra("kategori", kategoriProduk[index])
                intent.putExtra("gambar", gambarProduk[index])
                startActivity(intent)
            } catch (e: Exception) {
                Log.e(TAG, "Error saat membuka detail: ${e.message}")
                Toast.makeText(this, "Gagal membuka detail produk", Toast.LENGTH_SHORT).show()
            }
        }

        return kartu
    }
}