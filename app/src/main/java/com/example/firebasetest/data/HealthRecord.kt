package com.example.firebasetest.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "health_records")
data class HealthRecord(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val dateTime: Date,
    val weight: Double,
    val bloodPressure: String,
    val notes: String
)