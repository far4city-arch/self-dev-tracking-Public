package com.selfdev.tracking.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FamilyRestroom
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Savings
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * الخطة السباعية (رحلة الإنجاز: 7 خطوات نحو مستقبل مزدهر) كما وردت في الصورة المرفقة.
 * تُعرض هذه الأهداف السبعة كبطاقات ثابتة في الصفحة الرئيسية، وكل هدف له سجل متابعة خاص به.
 */
data class LifeGoal(
    val id: String,
    val order: Int,
    val title: String,
    val gain: String,
    val icon: ImageVector
)

object LifeGoals {
    val all: List<LifeGoal> = listOf(
        LifeGoal(
            id = "life_goal_1",
            order = 1,
            title = "مسؤوليات المعيشة الأسرية",
            gain = "المكتسب: تطوير الأبناء ووضع مفهوم تربوي واضح لهم",
            icon = Icons.Filled.FamilyRestroom
        ),
        LifeGoal(
            id = "life_goal_2",
            order = 2,
            title = "مسؤولية الأسرة الكبيرة",
            gain = "المكتسب: توفير مصادر دخل إضافية في اليمن",
            icon = Icons.Filled.Groups
        ),
        LifeGoal(
            id = "life_goal_3",
            order = 3,
            title = "مسؤولية سمعة وتطور النجاح في العمل",
            gain = "المكتسب: تحسين الدخل من خلال مسار وظيفي أو عمل بديل",
            icon = Icons.Filled.TrendingUp
        ),
        LifeGoal(
            id = "life_goal_4",
            order = 4,
            title = "مسؤولية سداد جميع الالتزامات والإيجارات",
            gain = "المكتسب: مرونة في الادخار وإدارة الالتزامات المالية",
            icon = Icons.Filled.Savings
        ),
        LifeGoal(
            id = "life_goal_5",
            order = 5,
            title = "مسؤولية المحافظة وتجنب المخالفات والحوادث",
            gain = "المكتسب: البحث عن خيار شراء سيارة مناسبة كمثال",
            icon = Icons.Filled.DirectionsCar
        ),
        LifeGoal(
            id = "life_goal_6",
            order = 6,
            title = "مسؤولية الاستفادة من الحياة والاستمتاع باللحظات",
            gain = "المكتسب: إيجاد فرصة سفر أو هجرة مع العائلة لحياة أفضل",
            icon = Icons.Filled.Public
        ),
        LifeGoal(
            id = "life_goal_7",
            order = 7,
            title = "رفع الأداء المجتمعي",
            gain = "المكتسب: نيل مكانة قيادية في المجتمع اليمني والوطني (دعوة، سياسة، حوار، أنشطة رياضية)",
            icon = Icons.Filled.EmojiEvents
        )
    )

    fun byId(id: String): LifeGoal? = all.firstOrNull { it.id == id }
}
