package com.example.firebasetest.data

import androidx.room.*

@Dao
interface HealthRecordDao {
    @Insert
    suspend fun insert(healthRecord: HealthRecord)

    @Update
    suspend fun update(healthRecord: HealthRecord)

    @Delete
    suspend fun delete(healthRecord: HealthRecord)

    @Query("SELECT * FROM health_records")
    suspend fun getAll(): List<HealthRecord>
}