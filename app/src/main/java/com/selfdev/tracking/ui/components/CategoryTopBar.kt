package com.selfdev.tracking.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selfdev.tracking.data.GoalCategory

/**
 * الأيقونات الرئيسية أفقية في أعلى الشاشة، وتنسدل منها الأيقونات الفرعية عند الضغط عليها،
 * تمامًا وفق المواصفات المطلوبة.
 */
@Composable
fun CategoryTopBar(
    categories: List<GoalCategory>,
    onSubItemClick: (GoalCategory, String?) -> Unit
) {
    var expandedCategory by remember { mutableStateOf<GoalCategory?>(null) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)
                .padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            categories.forEach { category ->
                if (category != GoalCategory.MAIN_PLAN) {
                    CategoryIconButton(
                        category = category,
                        onClick = {
                            if (category.subItems.isEmpty()) {
                                onSubItemClick(category, null)
                            } else {
                                expandedCategory =
                                    if (expandedCategory == category) null else category
                            }
                        }
                    )
                }
            }
        }

        val current = expandedCategory
        if (current != null && current.subItems.isNotEmpty()) {
            Surface(
                color = MaterialTheme.colorScheme.surfaceVariant,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(vertical = 4.dp)) {
                    current.subItems.forEach { subItem ->
                        DropdownSubItemRow(
                            label = subItem,
                            onClick = {
                                onSubItemClick(current, subItem)
                                expandedCategory = null
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun CategoryIconButton(category: GoalCategory, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 8.dp)
            .widthIn(min = 68.dp)
    ) {
        Icon(
            imageVector = category.icon,
            contentDescription = category.title,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = category.title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center,
            maxLines = 2
        )
    }
}

@Composable
private fun DropdownSubItemRow(label: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium)
    }
}
