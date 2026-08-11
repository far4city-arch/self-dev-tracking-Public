package com.selfdev.tracking.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEmotions
import androidx.compose.material.icons.filled.DirectionsWalk
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.RecordVoiceOver
import androidx.compose.material.icons.filled.Checkroom
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * الأهداف السبعة (الخطة الرئيسية / السباعية) كما وردت في المواصفات.
 * كل هدف يمثل أيقونة رئيسية أفقية في الشاشة الرئيسية، ويحتوي على أيقونات فرعية تنسدل منه.
 */
enum class GoalCategory(
    val id: String,
    val title: String,
    val icon: ImageVector,
    val subItems: List<String>
) {
    MAIN_PLAN(
        id = "main_plan",
        title = "الخطة الرئيسية",
        icon = Icons.Filled.Flag,
        subItems = emptyList() // تُعرض كخانات فارغة في سطح المكتب لإضافة مهام حرة
    ),
    VOICE_DEVELOPMENT(
        id = "voice_development",
        title = "مهام التطوير الصوتي",
        icon = Icons.Filled.Mic,
        subItems = listOf("الصوت الجوفي", "الوتيرة البطيئة")
    ),
    MOVEMENT_DEVELOPMENT(
        id = "movement_development",
        title = "مهام التطوير الحركي",
        icon = Icons.Filled.DirectionsWalk,
        subItems = listOf(
            "المشي الرزين",
            "حركات الجسد الإدارية",
            "النظر الحاد",
            "طريقة السلام"
        )
    ),
    ACCENT_IMPROVEMENT(
        id = "accent_improvement",
        title = "تحسين اللهجة",
        icon = Icons.Filled.RecordVoiceOver,
        subItems = listOf("اللكنة الخليجية", "مصطلحات خليجية")
    ),
    MINDFUL_PRESENCE(
        id = "mindful_presence",
        title = "الحضور الذهني المباشر أثناء الحديث",
        icon = Icons.Filled.Psychology,
        subItems = listOf("لياقة التحليل", "القراءة", "المراقبة")
    ),
    SMILE_EXPRESSIONS(
        id = "smile_expressions",
        title = "إدارة الابتسامة وتعابير الوجه",
        icon = Icons.Filled.EmojiEmotions,
        subItems = emptyList()
    ),
    APPEARANCE(
        id = "appearance",
        title = "المظهر الخارجي",
        icon = Icons.Filled.Checkroom,
        subItems = listOf("اللبس المناسب", "الإكسسوارات", "العطور")
    );

    companion object {
        val all: List<GoalCategory> = entries
    }
}
