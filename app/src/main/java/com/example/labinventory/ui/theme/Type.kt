package com.example.labinventory.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * Responsive typography system that adapts to different screen sizes and orientations
 */
object ResponsiveTypography {
    
    // Screen size breakpoints
    private const val PHONE_BREAKPOINT = 600
    private const val TABLET_BREAKPOINT = 840
    private const val LARGE_TABLET_BREAKPOINT = 1200
    
    // Orientation multipliers
    private const val LANDSCAPE_MULTIPLIER = 0.9f
    private const val PORTRAIT_MULTIPLIER = 1.0f
    
    // Base typography values
    private val baseTypography = BaseTypography()
    
    @Composable
    fun getResponsiveTypography(): ResponsiveTypographyValues {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val screenHeight = configuration.screenHeightDp
        val isLandscape = screenWidth > screenHeight
        
        return remember(screenWidth, screenHeight, isLandscape) {
            createResponsiveTypography(screenWidth, screenHeight, isLandscape)
        }
    }
    
    private fun createResponsiveTypography(
        screenWidth: Int,
        screenHeight: Int,
        isLandscape: Boolean
    ): ResponsiveTypographyValues {
        
        // Determine device type
        val deviceType = when {
            screenWidth >= LARGE_TABLET_BREAKPOINT -> DeviceType.LARGE_TABLET
            screenWidth >= TABLET_BREAKPOINT -> DeviceType.TABLET
            screenWidth >= PHONE_BREAKPOINT -> DeviceType.LARGE_PHONE
            else -> DeviceType.PHONE
        }
        
        // Calculate orientation multiplier
        val orientationMultiplier = if (isLandscape) LANDSCAPE_MULTIPLIER else PORTRAIT_MULTIPLIER
        
        // Calculate size multiplier based on device type
        val sizeMultiplier = when (deviceType) {
            DeviceType.PHONE -> 1.0f
            DeviceType.LARGE_PHONE -> 1.05f
            DeviceType.TABLET -> 1.15f
            DeviceType.LARGE_TABLET -> 1.25f
        }
        
        // Apply responsive scaling
        val finalMultiplier = sizeMultiplier * orientationMultiplier
        
        return ResponsiveTypographyValues(
            deviceType = deviceType,
            isLandscape = isLandscape,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            multiplier = finalMultiplier,
            baseTypography = baseTypography
        )
    }
}

/**
 * Base typography containing the original values
 */
data class BaseTypography(
    val bodyLargeFontSize: Float = 16f,
    val bodyLargeLineHeight: Float = 24f,
    val bodyLargeLetterSpacing: Float = 0.5f,
    val labelSmallFontSize: Float = 16f,
    val labelSmallLineHeight: Float = 16f,
    val labelSmallLetterSpacing: Float = 0.5f,
    val labelSmallFontWeight: FontWeight = FontWeight.Medium
)

/**
 * Responsive typography values that adapt to screen size and orientation
 */
data class ResponsiveTypographyValues(
    val deviceType: DeviceType,
    val isLandscape: Boolean,
    val screenWidth: Int,
    val screenHeight: Int,
    val multiplier: Float,
    val baseTypography: BaseTypography
) {
    
    // Responsive typography styles
    val bodyLarge: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = (baseTypography.bodyLargeFontSize * multiplier).sp,
        lineHeight = (baseTypography.bodyLargeLineHeight * multiplier).sp,
        letterSpacing = (baseTypography.bodyLargeLetterSpacing * multiplier).sp
    )
    
    val labelSmall: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = baseTypography.labelSmallFontWeight,
        fontSize = (baseTypography.labelSmallFontSize * multiplier).sp,
        lineHeight = (baseTypography.labelSmallLineHeight * multiplier).sp,
        letterSpacing = (baseTypography.labelSmallLetterSpacing * multiplier).sp
    )
    
    // Additional responsive typography styles
    val h1: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 24.sp
            DeviceType.LARGE_PHONE -> 26.sp
            DeviceType.TABLET -> 32.sp
            DeviceType.LARGE_TABLET -> 36.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 32.sp
            DeviceType.LARGE_PHONE -> 34.sp
            DeviceType.TABLET -> 40.sp
            DeviceType.LARGE_TABLET -> 44.sp
        }
    )
    
    val h2: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 20.sp
            DeviceType.LARGE_PHONE -> 22.sp
            DeviceType.TABLET -> 26.sp
            DeviceType.LARGE_TABLET -> 30.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 26.sp
            DeviceType.LARGE_PHONE -> 28.sp
            DeviceType.TABLET -> 32.sp
            DeviceType.LARGE_TABLET -> 36.sp
        }
    )
    
    val h3: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 18.sp
            DeviceType.LARGE_PHONE -> 20.sp
            DeviceType.TABLET -> 24.sp
            DeviceType.LARGE_TABLET -> 28.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 24.sp
            DeviceType.LARGE_PHONE -> 26.sp
            DeviceType.TABLET -> 30.sp
            DeviceType.LARGE_TABLET -> 34.sp
        }
    )
    
    val bodyMedium: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 14.sp
            DeviceType.LARGE_PHONE -> 15.sp
            DeviceType.TABLET -> 17.sp
            DeviceType.LARGE_TABLET -> 19.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 20.sp
            DeviceType.LARGE_PHONE -> 21.sp
            DeviceType.TABLET -> 23.sp
            DeviceType.LARGE_TABLET -> 25.sp
        }
    )
    
    val bodySmall: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 12.sp
            DeviceType.LARGE_PHONE -> 13.sp
            DeviceType.TABLET -> 15.sp
            DeviceType.LARGE_TABLET -> 17.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 16.sp
            DeviceType.LARGE_PHONE -> 17.sp
            DeviceType.TABLET -> 19.sp
            DeviceType.LARGE_TABLET -> 21.sp
        }
    )
    
    val caption: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 10.sp
            DeviceType.LARGE_PHONE -> 11.sp
            DeviceType.TABLET -> 13.sp
            DeviceType.LARGE_TABLET -> 15.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 14.sp
            DeviceType.LARGE_PHONE -> 15.sp
            DeviceType.TABLET -> 17.sp
            DeviceType.LARGE_TABLET -> 19.sp
        }
    )
    
    val button: TextStyle get() = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = when (deviceType) {
            DeviceType.PHONE -> 14.sp
            DeviceType.LARGE_PHONE -> 15.sp
            DeviceType.TABLET -> 17.sp
            DeviceType.LARGE_TABLET -> 19.sp
        },
        lineHeight = when (deviceType) {
            DeviceType.PHONE -> 20.sp
            DeviceType.LARGE_PHONE -> 21.sp
            DeviceType.TABLET -> 23.sp
            DeviceType.LARGE_TABLET -> 25.sp
        }
    )
}

/**
 * Legacy Typography object for backward compatibility
 * This maintains the existing API while providing responsive capabilities
 */
object Typography {
    @Composable
    fun getResponsive(): ResponsiveTypographyValues {
        return ResponsiveTypography.getResponsiveTypography()
    }
    
    // Legacy functions that now return responsive values
    @Composable
    fun bodyLarge(): TextStyle = getResponsive().bodyLarge
    @Composable
    fun labelSmall(): TextStyle = getResponsive().labelSmall
    
    // Additional responsive typography styles
    @Composable
    fun h1(): TextStyle = getResponsive().h1
    @Composable
    fun h2(): TextStyle = getResponsive().h2
    @Composable
    fun h3(): TextStyle = getResponsive().h3
    @Composable
    fun bodyMedium(): TextStyle = getResponsive().bodyMedium
    @Composable
    fun bodySmall(): TextStyle = getResponsive().bodySmall
    @Composable
    fun caption(): TextStyle = getResponsive().caption
    @Composable
    fun button(): TextStyle = getResponsive().button
}