package com.selfdev.tracking.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.selfdev.tracking.data.CategoryResolver
import com.selfdev.tracking.ui.components.AddEntryDialog
import com.selfdev.tracking.viewmodel.TrackingViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun CategoryDetailScreen(
    categoryId: String,
    preselectedSubItem: String?,
    onBack: () -> Unit,
    viewModel: TrackingViewModel = viewModel()
) {
    val resolved = remember(categoryId, preselectedSubItem) {
        CategoryResolver.resolve(categoryId, preselectedSubItem)
    }
    val entries by viewModel.entriesFor(categoryId).collectAsState(initial = emptyList())
    var showAddDialog by remember { mutableStateOf(false) }
    val dateFormat = remember { SimpleDateFormat("yyyy/MM/dd - hh:mm a", Locale("ar")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(resolved.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "رجوع")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Filled.Add, contentDescription = "إضافة إجراء متابعة")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            if (!resolved.description.isNullOrBlank()) {
                Text(
                    text = resolved.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                )
            }
        if (entries.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f, fill = false).padding(24.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text(
                    "لا توجد إجراءات بعد، اضغط + للإضافة",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(entries) { entry ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = entry.title,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                if (!entry.subItem.isNullOrBlank()) {
                                    Text(
                                        text = entry.subItem,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                Text(
                                    text = entry.body,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = dateFormat.format(Date(entry.createdAt)),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = "حذف",
                                modifier = Modifier.clickable { viewModel.deleteEntry(entry) }
                            )
                        }
                    }
                }
            }
        }
        }
    }

    if (showAddDialog) {
        AddEntryDialog(
            onDismiss = { showAddDialog = false },
            onConfirm = { text ->
                viewModel.addEntry(categoryId, preselectedSubItem, text)
                showAddDialog = false
            }
        )
    }
}
