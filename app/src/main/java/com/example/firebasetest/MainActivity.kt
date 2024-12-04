package com.example.firebasetest

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {


    private val db = FirebaseFirestore.getInstance()


    private var dataProvinsi = ArrayList<Map<String, String>>()
    private lateinit var lvAdapter: SimpleAdapter
    private lateinit var etProvinsi: EditText
    private lateinit var etIbukota: EditText
    private lateinit var btnSimpan: Button
    private lateinit var lvData: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        etProvinsi = findViewById(R.id.etProvinsi)
        etIbukota = findViewById(R.id.etIbukota)
        btnSimpan = findViewById(R.id.btnSimpan)
        lvData = findViewById(R.id.lvData)


        lvAdapter = SimpleAdapter(
            this,
            dataProvinsi,
            android.R.layout.simple_list_item_2,
            arrayOf("Provinsi", "Ibukota"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        lvData.adapter = lvAdapter


        btnSimpan.setOnClickListener {
            val provinsi = etProvinsi.text.toString()
            val ibukota = etIbukota.text.toString()
            if (provinsi.isNotEmpty() && ibukota.isNotEmpty()) {
                tambahData(provinsi, ibukota)
            } else {
                Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }


        readData()
    }


    private fun tambahData(provinsi: String, ibukota: String) {
        val dataBaru = mapOf("Provinsi" to provinsi, "Ibukota" to ibukota)
        db.collection("tbProvinsi")
            .add(dataBaru)
            .addOnSuccessListener {
                etProvinsi.text.clear()
                etIbukota.text.clear()
                Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
                readData() // Refresh data
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal menyimpan data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }


    private fun readData() {
        db.collection("tbProvinsi")
            .get()
            .addOnSuccessListener { result ->
                dataProvinsi.clear()
                for (document in result) {
                    val provinsi = document.getString("Provinsi").orEmpty()
                    val ibukota = document.getString("Ibukota").orEmpty()
                    dataProvinsi.add(mapOf("Provinsi" to provinsi, "Ibukota" to ibukota))
                }
                lvAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal mengambil data: ${it.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
