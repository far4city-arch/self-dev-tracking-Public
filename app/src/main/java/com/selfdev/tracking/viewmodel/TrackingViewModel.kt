package com.selfdev.tracking.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.selfdev.tracking.data.AppDatabase
import com.selfdev.tracking.data.TrackingEntry
import com.selfdev.tracking.data.TrackingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TrackingViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = TrackingRepository(
        AppDatabase.getInstance(application).trackingDao()
    )

    fun entriesFor(categoryId: String): Flow<List<TrackingEntry>> =
        repository.entriesFor(categoryId)

    fun addEntry(categoryId: String, subItem: String?, body: String) {
        viewModelScope.launch {
            repository.addEntry(categoryId, subItem, body)
        }
    }

    fun toggleDone(entry: TrackingEntry) {
        viewModelScope.launch { repository.toggleDone(entry) }
    }

    fun deleteEntry(entry: TrackingEntry) {
        viewModelScope.launch { repository.deleteEntry(entry) }
    }
}
