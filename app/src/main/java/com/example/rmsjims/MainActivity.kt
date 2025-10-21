package com.example.rmsjims

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.example.rmsjims.navigation.MainApp
import com.example.rmsjims.ui.theme.LabInventoryTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isChecking = true
        lifecycleScope.launch {
            delay(5000L)
            isChecking = false
        }
        installSplashScreen().apply {
            setKeepOnScreenCondition{
                isChecking
            }
        enableEdgeToEdge()
        setContent {
            LabInventoryTheme {
                MainApp()
            }
        }
    }
    
    fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        // Handle configuration changes (orientation, screen size, etc.)
        when (newConfig.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                // Handle landscape orientation
            }

            Configuration.ORIENTATION_PORTRAIT -> {
                // Handle portrait orientation
            }
        }

        // Handle screen size changes
        when (newConfig.screenLayout and Configuration.SCREENLAYOUT_SIZE_MASK) {
            Configuration.SCREENLAYOUT_SIZE_SMALL -> {
                // Small screen
            }

            Configuration.SCREENLAYOUT_SIZE_NORMAL -> {
                // Normal screen
            }

            Configuration.SCREENLAYOUT_SIZE_LARGE -> {
                // Large screen (tablet)
            }

            Configuration.SCREENLAYOUT_SIZE_XLARGE -> {
                // Extra large screen (large tablet)
            }
        }
    }
    }
}
