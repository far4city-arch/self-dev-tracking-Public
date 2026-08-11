package com.selfdev.tracking.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.selfdev.tracking.data.GoalCategory
import com.selfdev.tracking.data.LifeGoals
import com.selfdev.tracking.ui.components.CategoryTopBar
import com.selfdev.tracking.ui.components.SevenGoalsGrid

@Composable
fun HomeScreen(
    onNavigateToCategory: (String, String?) -> Unit
) {
    Scaffold(
        topBar = {
            Column {
                TopAppBar(title = { Text("المتابعة والتطوير الذاتي") })
                CategoryTopBar(
                    categories = GoalCategory.all,
                    onSubItemClick = { category, subItem ->
                        onNavigateToCategory(category.id, subItem)
                    }
                )
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Text(
                text = "رحلة الإنجاز: الخطة السباعية",
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(start = 16.dp, top = 12.dp)
            )
            Text(
                text = "7 خطوات نحو مستقبل مزدهر — اضغط أي مسؤولية لمتابعة إجراءاتها",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp, bottom = 4.dp)
            )
            SevenGoalsGrid(
                goals = LifeGoals.all,
                onGoalClick = { goal -> onNavigateToCategory(goal.id, null) }
            )
        }
    }
}
