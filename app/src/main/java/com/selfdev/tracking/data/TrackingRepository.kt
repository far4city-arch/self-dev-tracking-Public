package com.selfdev.tracking.data

import com.selfdev.tracking.util.TitleSummarizer
import kotlinx.coroutines.flow.Flow

class TrackingRepository(private val dao: TrackingDao) {

    fun entriesFor(categoryId: String): Flow<List<TrackingEntry>> =
        dao.getEntriesForCategory(categoryId)

    fun allEntries(): Flow<List<TrackingEntry>> = dao.getAllEntries()

    suspend fun addEntry(categoryId: String, subItem: String?, body: String) {
        // عنوان الإجراء يُلخص آليًا بأقل عدد ممكن من الكلمات، وتاريخ الإضافة يُسجل تلقائيًا
        val autoTitle = TitleSummarizer.summarize(body)
        dao.insert(
            TrackingEntry(
                categoryId = categoryId,
                subItem = subItem,
                title = autoTitle,
                body = body,
                createdAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun toggleDone(entry: TrackingEntry) {
        dao.update(entry.copy(isDone = !entry.isDone))
    }

    suspend fun deleteEntry(entry: TrackingEntry) {
        dao.delete(entry)
    }
}
