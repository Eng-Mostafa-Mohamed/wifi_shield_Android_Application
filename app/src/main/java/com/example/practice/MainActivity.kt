package com.example.practice

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.example.data.core.modules.SessionManager
import com.example.practice.presentation.screens.AppNavGraph
import com.example.practice.ui.theme.PracticeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SessionManager.init(applicationContext)

        setContent {
            PracticeTheme {
                AppNavGraph()
            }
        }
    }
}

@SuppressLint("ContextCastToActivity")
@Composable
fun SetStatusBarColor(color: Color, darkIcons: Boolean) {
    val activity = LocalContext.current as Activity

    SideEffect {
        activity.window.statusBarColor = color.toArgb()
        WindowCompat.getInsetsController(
            activity.window,
            activity.window.decorView
        ).isAppearanceLightStatusBars = darkIcons
    }
}
