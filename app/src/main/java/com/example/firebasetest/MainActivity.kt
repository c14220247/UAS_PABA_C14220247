package com.example.firebasetest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.adapter.HealthRecordAdapter
import com.example.firebasetest.data.AppDatabase
import com.example.firebasetest.data.HealthRecord
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class MainActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()
    private lateinit var etWeight: EditText
    private lateinit var etBloodPressure: EditText
    private lateinit var etNotes: EditText
    private lateinit var btnSave: Button
    private lateinit var rvHealthRecords: RecyclerView
    private lateinit var btnUploadDownload: Button

    private lateinit var healthRecordAdapter: HealthRecordAdapter
    private val healthRecords = mutableListOf<HealthRecord>()

    private val database by lazy { AppDatabase.getDatabase(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etWeight = findViewById(R.id.etWeight)
        etBloodPressure = findViewById(R.id.etBloodPressure)
        etNotes = findViewById(R.id.etNotes)
        btnSave = findViewById(R.id.btnSave)
        rvHealthRecords = findViewById(R.id.rvHealthRecords)
        btnUploadDownload = findViewById(R.id.btnUploadDownload)

        healthRecordAdapter = HealthRecordAdapter(healthRecords)
        rvHealthRecords.adapter = healthRecordAdapter
        rvHealthRecords.layoutManager = LinearLayoutManager(this)

        btnSave.setOnClickListener {
            saveHealthRecord()
        }

        btnUploadDownload.setOnClickListener {
            startActivity(Intent(this, UploadDownloadActivity::class.java))
        }

        loadHealthRecords()
    }

    private fun saveHealthRecord() {
        val weight = etWeight.text.toString().toDoubleOrNull()
        val bloodPressure = etBloodPressure.text.toString()
        val notes = etNotes.text.toString()

        if (weight != null && bloodPressure.isNotEmpty() && notes.isNotEmpty()) {
            val healthRecord = HealthRecord(
                dateTime = Date(),
                weight = weight,
                bloodPressure = bloodPressure,
                notes = notes
            )

            CoroutineScope(Dispatchers.IO).launch {
                database.healthRecordDao().insert(healthRecord)
                loadHealthRecords()
            }

            etWeight.text.clear()
            etBloodPressure.text.clear()
            etNotes.text.clear()

            Toast.makeText(this, "Data berhasil disimpan", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Field tidak boleh kosong", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadHealthRecords() {
        CoroutineScope(Dispatchers.IO).launch {
            val records = database.healthRecordDao().getAll() // Fetch records from database
            runOnUiThread {
                healthRecords.clear()
                healthRecords.addAll(records)
                healthRecordAdapter.notifyDataSetChanged() // Update RecyclerView
            }
        }
    }
}