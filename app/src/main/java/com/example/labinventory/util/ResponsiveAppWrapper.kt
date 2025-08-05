package com.example.labinventory.util

import android.content.res.Configuration
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.example.labinventory.data.model.ResponsiveLayout
import com.example.labinventory.data.remote.LocalResponsiveLayout

@Composable
fun ResponsiveAppWrapper(
    content: @Composable () -> Unit
) {
    //gives access tp screen dimension
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
      //
        with(this) {
            val screenWidth = maxWidth
            val screenHeight = maxHeight

            //get current device config - screen orientation, font scale, etc
            val configuration = LocalConfiguration.current

            //instance of ResponsiveLayout
            val layoutInfo = ResponsiveLayout(
                screenWidth = screenWidth,
                screenHeight = screenHeight,
                topPadding = when {
                    screenWidth < 360.dp -> 8.dp
                    screenWidth < 600.dp -> 16.dp
                    else -> 24.dp
                },
                isTablet = screenWidth > 600.dp,
                //of the device is in potrait mode
                isPotrait = configuration.orientation == Configuration.ORIENTATION_PORTRAIT
            )

            //it shares the layout info with everythign inside the app screen so any composable can use it
            CompositionLocalProvider(LocalResponsiveLayout provides layoutInfo) {
                content()
            }
        }
    }
}
