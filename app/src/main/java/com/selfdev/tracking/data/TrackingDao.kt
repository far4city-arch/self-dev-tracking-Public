package com.selfdev.tracking.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackingDao {

    @Query("SELECT * FROM tracking_entries WHERE categoryId = :categoryId ORDER BY createdAt DESC")
    fun getEntriesForCategory(categoryId: String): Flow<List<TrackingEntry>>

    @Query("SELECT * FROM tracking_entries ORDER BY createdAt DESC")
    fun getAllEntries(): Flow<List<TrackingEntry>>

    @Insert
    suspend fun insert(entry: TrackingEntry): Long

    @Update
    suspend fun update(entry: TrackingEntry)

    @Delete
    suspend fun delete(entry: TrackingEntry)
}
