package com.example.katalog_makeup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    val namaProduk = arrayOf("Lipstick Ruby Woo", "Eyeshadow Palette", "Foundation Fit Me", "Blush On Matte")
    val brandProduk = arrayOf("MAC", "Maybelline", "NYX", "Wardah")
    val hargaProduk = arrayOf("Rp 415.000", "Rp 189.000", "Rp 125.000", "Rp 65.000")
    val kategoriProduk = arrayOf("Bibir", "Mata", "Wajah", "Pipi")
    val gambarProduk = arrayOf("lipstik", "eyeshadow", "foundition", "blush")

    val urутanTerlaris = arrayOf(0, 3, 2, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etCari = findViewById<EditText>(R.id.etCari)
        val btnCari = findViewById<Button>(R.id.btnCari)
        val btnSortAZ = findViewById<Button>(R.id.btnSortAZ)
        val btnTerlaris = findViewById<Button>(R.id.btnTerlaris)

        tampilkanProduk(arrayOf(0, 1, 2, 3))
        btnCari.setOnClickListener {
            val keyword = etCari.text.toString().trim()
            if (keyword.isEmpty()) {
                Toast.makeText(this, "Kolom pencarian tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            } else {
                val hasilSearch = linearSearch(keyword)
                if (hasilSearch.isEmpty()) {
                    Toast.makeText(this, "Produk '$keyword' tidak ditemukan!", Toast.LENGTH_SHORT).show()
                } else {
                    tampilkanProduk(hasilSearch.toTypedArray())
                    Toast.makeText(this, "${hasilSearch.size} produk ditemukan", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnSortAZ.setOnClickListener {
            val sorted = bubbleSort(ascending = true)
            tampilkanProduk(sorted)
            Toast.makeText(this, "Diurutkan A-Z", Toast.LENGTH_SHORT).show()
        }

        btnTerlaris.setOnClickListener {
            tampilkanProduk(urутanTerlaris)
            Toast.makeText(this, "Menampilkan produk terlaris", Toast.LENGTH_SHORT).show()
        }
    }
    fun linearSearch(keyword: String): List<Int> {
        val hasil = mutableListOf<Int>()
        for (i in namaProduk.indices) {
            if (namaProduk[i].lowercase().contains(keyword.lowercase())) {
                hasil.add(i)
            }
        }
        return hasil
    }
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
    fun buatKartu(index: Int): LinearLayout {
        val kartu = LinearLayout(this)
        kartu.orientation = LinearLayout.VERTICAL
        kartu.setBackgroundResource(R.drawable.bg_card)
        kartu.isClickable = true
        kartu.isFocusable = true

        val paramKartu = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        paramKartu.setMargins(8, 8, 8, 8)
        kartu.layoutParams = paramKartu

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

        kartu.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("nama", namaProduk[index])
            intent.putExtra("brand", brandProduk[index])
            intent.putExtra("harga", hargaProduk[index])
            intent.putExtra("kategori", kategoriProduk[index])
            intent.putExtra("gambar", gambarProduk[index])
            startActivity(intent)
        }

        return kartu
    }
}