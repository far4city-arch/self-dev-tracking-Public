package com.selfdev.tracking.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.platform.LocalLayoutDirection

// وفق المواصفات: الألوان غامقة دائمًا بغض النظر عن إعداد النظام
private val DarkScheme = darkColorScheme(
    primary = PrimaryGold,
    onPrimary = BackgroundDark,
    secondary = PrimaryGoldVariant,
    background = BackgroundDark,
    onBackground = OnBackground,
    surface = SurfaceDark,
    onSurface = OnBackground,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceMuted,
    error = ErrorRed
)

@Composable
fun SelfDevTrackingTheme(content: @Composable () -> Unit) {
    // إجبار اتجاه الواجهة بالكامل من اليمين إلى اليسار لأن التطبيق عربي بالكامل
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        MaterialTheme(
            colorScheme = DarkScheme,
            typography = AppTypography,
            content = content
        )
    }
}
