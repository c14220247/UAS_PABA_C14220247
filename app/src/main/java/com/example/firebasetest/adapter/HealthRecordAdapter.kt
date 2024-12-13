package com.example.firebasetest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.firebasetest.R
import com.example.firebasetest.data.HealthRecord
import java.text.SimpleDateFormat
import java.util.*

class HealthRecordAdapter(private val healthRecords: List<HealthRecord>) :
    RecyclerView.Adapter<HealthRecordAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDateTime: TextView = itemView.findViewById(R.id.tvDateTime)
        val tvWeight: TextView = itemView.findViewById(R.id.tvWeight)
        val tvBloodPressure: TextView = itemView.findViewById(R.id.tvBloodPressure)
        val tvNotes: TextView = itemView.findViewById(R.id.tvNotes)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_health_record, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = healthRecords[position]
        val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault())
        holder.tvDateTime.text = dateFormat.format(currentItem.dateTime)
        holder.tvWeight.text = "Berat Badan: ${currentItem.weight} kg"
        holder.tvBloodPressure.text = "Tekanan Darah: ${currentItem.bloodPressure}"
        holder.tvNotes.text = "Catatan: ${currentItem.notes}"
    }

    override fun getItemCount(): Int {
        return healthRecords.size
    }
}