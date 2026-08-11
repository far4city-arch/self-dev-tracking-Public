package com.selfdev.tracking

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.selfdev.tracking.notification.AlarmScheduler
import com.selfdev.tracking.notification.FlashSignal
import com.selfdev.tracking.ui.screens.CategoryDetailScreen
import com.selfdev.tracking.ui.screens.HomeScreen
import com.selfdev.tracking.ui.theme.SelfDevTrackingTheme

/**
 * لا توجد أي شاشة دخول أو كلمة مرور: التطبيق يفتح مباشرة على الشاشة الرئيسية،
 * وحجم الواجهة والموارد مبقى عليه في أدنى حد ممكن وفق المواصفات.
 */
class MainActivity : ComponentActivity() {

    private val notificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermissionIfNeeded()
        AlarmScheduler.scheduleAll(applicationContext)

        setContent {
            SelfDevTrackingTheme {
                AppRoot()
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val granted = ContextCompat.checkSelfPermission(
                this, Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!granted) {
                notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}

@Composable
private fun AppRoot() {
    val navController = rememberNavController()

    // تأثير الوميض البصري في الشاشة الرئيسية عند وجود تنبيه بصري معلّق (الساعة 10 صباحًا)
    var flashAlpha by remember { mutableFloatStateOf(0f) }
    LaunchedEffect(Unit) {
        if (FlashSignal.pending) {
            FlashSignal.pending = false
        }
    }

    val infiniteFlash = remember { Animatable(0f) }
    LaunchedEffect(FlashSignal.pending) {
        if (FlashSignal.pending) {
            repeat(4) {
                infiniteFlash.animateTo(1f, animationSpec = tween(250))
                infiniteFlash.animateTo(0f, animationSpec = tween(250))
            }
            FlashSignal.pending = false
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NavHost(navController = navController, startDestination = "home") {
            composable("home") {
                HomeScreen(
                    onNavigateToCategory = { categoryId, subItem ->
                        navController.navigate("category/$categoryId?subItem=${subItem ?: ""}")
                    }
                )
            }
            composable(
                route = "category/{categoryId}?subItem={subItem}",
                arguments = listOf(
                    navArgument("categoryId") { type = NavType.StringType },
                    navArgument("subItem") {
                        type = NavType.StringType
                        defaultValue = ""
                    }
                )
            ) { backStackEntry ->
                val categoryId = backStackEntry.arguments?.getString("categoryId") ?: return@composable
                val subItem = backStackEntry.arguments?.getString("subItem")?.takeIf { it.isNotBlank() }
                CategoryDetailScreen(
                    categoryId = categoryId,
                    preselectedSubItem = subItem,
                    onBack = { navController.popBackStack() }
                )
            }
        }

        if (infiniteFlash.value > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(alpha = infiniteFlash.value * 0.25f))
            )
        }
    }
}
