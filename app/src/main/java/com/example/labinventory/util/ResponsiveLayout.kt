package com.example.labinventory.util

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.TextUnit
import com.example.labinventory.ui.theme.DeviceType
import com.example.labinventory.ui.theme.ResponsiveDimensions

/**
 * Responsive layout utilities for different screen sizes and orientations
 */
object ResponsiveLayout {
    
    /**
     * Get responsive grid columns based on device type
     */
    @Composable
    fun getGridColumns(): GridCells {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> GridCells.Fixed(2)
            DeviceType.LARGE_PHONE -> GridCells.Fixed(2)
            DeviceType.TABLET -> GridCells.Fixed(3)
            DeviceType.LARGE_TABLET -> GridCells.Fixed(4)
        }
    }
    
    /**
     * Get responsive grid columns with custom phone columns
     */
    @Composable
    fun getGridColumns(phoneColumns: Int, tabletColumns: Int, largeTabletColumns: Int): GridCells {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> GridCells.Fixed(phoneColumns)
            DeviceType.LARGE_PHONE -> GridCells.Fixed(phoneColumns)
            DeviceType.TABLET -> GridCells.Fixed(tabletColumns)
            DeviceType.LARGE_TABLET -> GridCells.Fixed(largeTabletColumns)
        }
    }
    
    /**
     * Get responsive horizontal padding
     */
    @Composable
    fun getHorizontalPadding(): Dp {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return dimensions.horizontalPadding
    }
    
    /**
     * Get responsive vertical padding
     */
    @Composable
    fun getVerticalPadding(): Dp {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return dimensions.verticalPadding
    }
    
    /**
     * Get responsive card spacing
     */
    @Composable
    fun getCardSpacing(): Dp {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return dimensions.cardSpacing
    }
    
    /**
     * Get responsive content padding for LazyVerticalGrid
     */
    @Composable
    fun getContentPadding(): PaddingValues {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return PaddingValues(
            horizontal = dimensions.horizontalPadding,
            vertical = dimensions.verticalPadding
        )
    }
    
    /**
     * Get responsive arrangement spacing for LazyVerticalGrid
     */
    @Composable
    fun getGridArrangement(): Arrangement.Horizontal {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return Arrangement.spacedBy(dimensions.cardSpacing)
    }
    
    /**
     * Get responsive vertical arrangement spacing for LazyVerticalGrid
     */
    @Composable
    fun getVerticalGridArrangement(): Arrangement.Vertical {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return Arrangement.spacedBy(dimensions.cardSpacing)
    }
    
    /**
     * Get responsive horizontal arrangement spacing for LazyRow
     */
    @Composable
    fun getRowArrangement(): Arrangement.Horizontal {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return Arrangement.spacedBy(dimensions.cardSpacing)
    }
    
    /**
     * Get responsive padding values for different screen sizes
     */
    @Composable
    fun getResponsivePadding(
        phone: Dp = 16.dp,
        tablet: Dp = 32.dp,
        largeTablet: Dp = 48.dp
    ): Dp {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> phone
            DeviceType.LARGE_PHONE -> phone
            DeviceType.TABLET -> tablet
            DeviceType.LARGE_TABLET -> largeTablet
        }
    }
    
    /**
     * Get responsive size values for different screen sizes
     */
    @Composable
    fun getResponsiveSize(
        phone: Dp,
        tablet: Dp,
        largeTablet: Dp
    ): Dp {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> phone
            DeviceType.LARGE_PHONE -> phone
            DeviceType.TABLET -> tablet
            DeviceType.LARGE_TABLET -> largeTablet
        }
    }
    
    /**
     * Get responsive font size values for different screen sizes
     */
    @Composable
    fun getResponsiveFontSize(
        phone: TextUnit,
        tablet: TextUnit,
        largeTablet: TextUnit
    ): TextUnit {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> phone
            DeviceType.LARGE_PHONE -> phone
            DeviceType.TABLET -> tablet
            DeviceType.LARGE_TABLET -> largeTablet
        }
    }
    
    /**
     * Get responsive boolean values for different screen sizes
     */
    @Composable
    fun getResponsiveBoolean(
        phone: Boolean = false,
        tablet: Boolean = true,
        largeTablet: Boolean = true
    ): Boolean {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> phone
            DeviceType.LARGE_PHONE -> phone
            DeviceType.TABLET -> tablet
            DeviceType.LARGE_TABLET -> largeTablet
        }
    }
    
    /**
     * Get responsive integer values for different screen sizes
     */
    @Composable
    fun getResponsiveInt(
        phone: Int,
        tablet: Int,
        largeTablet: Int
    ): Int {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> phone
            DeviceType.LARGE_PHONE -> phone
            DeviceType.TABLET -> tablet
            DeviceType.LARGE_TABLET -> largeTablet
        }
    }
    
    /**
     * Check if current device is a tablet
     */
    @Composable
    fun isTablet(): Boolean {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return dimensions.deviceType == DeviceType.TABLET || dimensions.deviceType == DeviceType.LARGE_TABLET
    }
    
    /**
     * Check if current device is a phone
     */
    @Composable
    fun isPhone(): Boolean {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return dimensions.deviceType == DeviceType.PHONE || dimensions.deviceType == DeviceType.LARGE_PHONE
    }
    
    /**
     * Check if current orientation is landscape
     */
    @Composable
    fun isLandscape(): Boolean {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return dimensions.isLandscape
    }
    
    /**
     * Get responsive aspect ratio for different screen sizes
     */
    @Composable
    fun getResponsiveAspectRatio(
        phone: Float = 1f,
        tablet: Float = 1.2f,
        largeTablet: Float = 1.4f
    ): Float {
        val dimensions = ResponsiveDimensions.getResponsiveDimensions()
        return when (dimensions.deviceType) {
            DeviceType.PHONE -> phone
            DeviceType.LARGE_PHONE -> phone
            DeviceType.TABLET -> tablet
            DeviceType.LARGE_TABLET -> largeTablet
        }
    }
}
