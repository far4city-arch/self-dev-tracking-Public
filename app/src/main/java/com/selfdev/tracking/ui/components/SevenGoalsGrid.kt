package com.selfdev.tracking.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.selfdev.tracking.data.LifeGoal

/**
 * الصفحة الرئيسية / سطح المكتب: بطاقات الخطة السباعية (رحلة الإنجاز: 7 خطوات نحو مستقبل مزدهر).
 * كل بطاقة تمثل مسؤولية ثابتة من الخطة، والضغط عليها يفتح سجل متابعة خاصًا بها
 * حيث يمكن إضافة إجراءات المتابعة عبر علامة (+).
 */
@Composable
fun SevenGoalsGrid(
    goals: List<LifeGoal>,
    onGoalClick: (LifeGoal) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(goals, key = { it.id }) { goal ->
            GoalSlot(goal = goal, onClick = { onGoalClick(goal) })
        }
    }
}

@Composable
private fun GoalSlot(goal: LifeGoal, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .height(130.dp)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = goal.icon,
                contentDescription = goal.title,
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "${goal.order}. ${goal.title}",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                maxLines = 3
            )
        }
    }
}
