package com.selfdev.tracking.data

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * إجراء متابعة واحد يُضاف عبر علامة (+) داخل أي هدف من الأهداف السبعة.
 * - createdAt: تاريخ إجراءات المتابعة، يظهر آليًا بحسب تاريخ كتابة الإجراء.
 * - title: عنوان الإجراء المدخل، يُلخص آليًا بأقل عدد ممكن من الكلمات.
 */
@Entity(tableName = "tracking_entries")
data class TrackingEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val categoryId: String,
    val subItem: String? = null,
    val title: String,
    val body: String,
    val createdAt: Long = System.currentTimeMillis(),
    val isDone: Boolean = false
)
