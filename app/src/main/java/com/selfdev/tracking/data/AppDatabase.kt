package com.selfdev.tracking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TrackingEntry::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trackingDao(): TrackingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                // قاعدة بيانات محلية بالكامل - لا حاجة لأي اتصال إنترنت أو حساب مستخدم
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "self_dev_tracking.db"
                ).build().also { INSTANCE = it }
            }
        }
    }
}
