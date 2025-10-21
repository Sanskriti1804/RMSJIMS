package com.example.rmsjims.util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.rmsjims.ui.theme.DeviceType
import com.example.rmsjims.ui.theme.ResponsiveDimensions

/**
 * Responsive wrapper that adapts content based on screen size and orientation
 */
@Composable
fun ResponsiveAppWrapper(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val dimensions = ResponsiveDimensions.getResponsiveDimensions()
    
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(
                start = dimensions.horizontalPadding,
                end = dimensions.horizontalPadding,
                top = dimensions.verticalPadding,
                bottom = dimensions.verticalPadding
            ),
        contentAlignment = Alignment.TopStart
    ) {
        content()
    }
}

/**
 * Responsive content wrapper with different behaviors for phone/tablet
 */
@Composable
fun ResponsiveContentWrapper(
    phoneContent: @Composable () -> Unit,
    tabletContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = ResponsiveDimensions.getResponsiveDimensions()
    
    when (dimensions.deviceType) {
        DeviceType.PHONE, DeviceType.LARGE_PHONE -> phoneContent()
        DeviceType.TABLET, DeviceType.LARGE_TABLET -> tabletContent()
    }
}

/**
 * Responsive orientation wrapper
 */
@Composable
fun ResponsiveOrientationWrapper(
    portraitContent: @Composable () -> Unit,
    landscapeContent: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val dimensions = ResponsiveDimensions.getResponsiveDimensions()
    
    if (dimensions.isLandscape) {
        landscapeContent()
    } else {
        portraitContent()
    }
}
