package com.example.labinventory.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Responsive dimensions system that adapts to different screen sizes and orientations
 * while maintaining the existing hardcoded values as defaults
 */
object ResponsiveDimensions {

    // Screen size breakpoints
    private const val PHONE_BREAKPOINT = 600
    private const val TABLET_BREAKPOINT = 840
    private const val LARGE_TABLET_BREAKPOINT = 1200

    // Orientation multipliers
    private const val LANDSCAPE_MULTIPLIER = 0.8f
    private const val PORTRAIT_MULTIPLIER = 1.0f

    // Base dimensions (existing hardcoded values)
    private val baseDimensions = BaseDimensions()

    @Composable
    fun getResponsiveDimensions(): ResponsiveDimensionValues {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp
        val screenHeight = configuration.screenHeightDp
        val isLandscape = screenWidth > screenHeight

        return remember(screenWidth, screenHeight, isLandscape) {
            createResponsiveDimensions(screenWidth, screenHeight, isLandscape)
        }
    }

    private fun createResponsiveDimensions(
        screenWidth: Int,
        screenHeight: Int,
        isLandscape: Boolean
    ): ResponsiveDimensionValues {

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
            DeviceType.LARGE_PHONE -> 1.1f
            DeviceType.TABLET -> 1.3f
            DeviceType.LARGE_TABLET -> 1.5f
        }

        // Apply responsive scaling
        val finalMultiplier = sizeMultiplier * orientationMultiplier

        return ResponsiveDimensionValues(
            deviceType = deviceType,
            isLandscape = isLandscape,
            screenWidth = screenWidth,
            screenHeight = screenHeight,
            multiplier = finalMultiplier,
            baseDimensions = baseDimensions
        )
    }
}

/**
 * Device type classification
 */
enum class DeviceType {
    PHONE,
    LARGE_PHONE,
    TABLET,
    LARGE_TABLET
}

/**
 * Base dimensions containing the original hardcoded values
 */
data class BaseDimensions(
    val appPadding: Dp = 6.dp,
    val smallSpacer: Dp = 2.dp,
    val medSpacer: Dp = 4.dp,
    val smallPadding: Dp = 8.dp,
    val mediumPadding: Dp = 12.dp,
    val largePadding: Dp = 18.dp,
    val componentPadding: Dp = 4.dp,
    val imagePadding: Dp = 8.dp,
    val iconPadding: Dp = 1.dp,
    val barPadding: Dp = 5.dp,
    val appLogoSize: Dp = 150.dp,
    val ProductSize: Dp = 100.dp,
    val productDescSize: Dp = 250.dp,
    val productCardSize: Dp = 180.dp,
    val cartCardSize: Dp = 80.dp,
    val iconSize: Dp = 30.dp,
    val profileSize: Dp = 120.dp,
    val AnimationSize: Dp = 300.dp,
    val reviewImageSize: Dp = 100.dp,
    val CategoryCardSize: Dp = 50.dp,
    val barProfileSize: Dp = 50.dp,
    val cardElevation: Dp = 2.dp,
    val buttonElevation: Dp = 2.dp,
    val cardElementsPadding: Dp = 8.dp,
    val buttonPadding: Dp = 3.dp,
    val buttonBorder: Dp = 1.dp,
    val profileBoder: Dp = 1.dp,
    val cardBorder: Dp = 1.dp
)

/**
 * Responsive dimension values that adapt to screen size and orientation
 */
data class ResponsiveDimensionValues(
    val deviceType: DeviceType,
    val isLandscape: Boolean,
    val screenWidth: Int,
    val screenHeight: Int,
    val multiplier: Float,
    val baseDimensions: BaseDimensions
) {

    // Responsive padding values
    val appPadding: Dp get() = (baseDimensions.appPadding.value * multiplier).dp
    val smallSpacer: Dp get() = (baseDimensions.smallSpacer.value * multiplier).dp
    val medSpacer: Dp get() = (baseDimensions.medSpacer.value * multiplier).dp
    val smallPadding: Dp get() = (baseDimensions.smallPadding.value * multiplier).dp
    val mediumPadding: Dp get() = (baseDimensions.mediumPadding.value * multiplier).dp
    val largePadding: Dp get() = (baseDimensions.largePadding.value * multiplier).dp
    val componentPadding: Dp get() = (baseDimensions.componentPadding.value * multiplier).dp
    val imagePadding: Dp get() = (baseDimensions.imagePadding.value * multiplier).dp
    val iconPadding: Dp get() = (baseDimensions.iconPadding.value * multiplier).dp
    val barPadding: Dp get() = (baseDimensions.barPadding.value * multiplier).dp

    // Responsive size values
    val appLogoSize: Dp get() = (baseDimensions.appLogoSize.value * multiplier).dp
    val ProductSize: Dp get() = (baseDimensions.ProductSize.value * multiplier).dp
    val productDescSize: Dp get() = (baseDimensions.productDescSize.value * multiplier).dp
    val productCardSize: Dp get() = (baseDimensions.productCardSize.value * multiplier).dp
    val cartCardSize: Dp get() = (baseDimensions.cartCardSize.value * multiplier).dp
    val iconSize: Dp get() = (baseDimensions.iconSize.value * multiplier).dp
    val profileSize: Dp get() = (baseDimensions.profileSize.value * multiplier).dp
    val AnimationSize: Dp get() = (baseDimensions.AnimationSize.value * multiplier).dp
    val reviewImageSize: Dp get() = (baseDimensions.reviewImageSize.value * multiplier).dp
    val CategoryCardSize: Dp get() = (baseDimensions.CategoryCardSize.value * multiplier).dp
    val barProfileSize: Dp get() = (baseDimensions.barProfileSize.value * multiplier).dp

    // Responsive elevation values
    val cardElevation: Dp get() = (baseDimensions.cardElevation.value * multiplier).dp
    val buttonElevation: Dp get() = (baseDimensions.buttonElevation.value * multiplier).dp

    // Responsive padding values
    val cardElementsPadding: Dp get() = (baseDimensions.cardElementsPadding.value * multiplier).dp
    val buttonPadding: Dp get() = (baseDimensions.buttonPadding.value * multiplier).dp

    // Responsive border values
    val buttonBorder: Dp get() = (baseDimensions.buttonBorder.value * multiplier).dp
    val profileBoder: Dp get() = (baseDimensions.profileBoder.value * multiplier).dp
    val cardBorder: Dp get() = (baseDimensions.cardBorder.value * multiplier).dp

    // Additional responsive values for different screen sizes
    val gridColumns: Int get() = when (deviceType) {
        DeviceType.PHONE -> 2
        DeviceType.LARGE_PHONE -> 2
        DeviceType.TABLET -> 3
        DeviceType.LARGE_TABLET -> 4
    }

    val horizontalPadding: Dp get() = when (deviceType) {
        DeviceType.PHONE -> 16.dp
        DeviceType.LARGE_PHONE -> 20.dp
        DeviceType.TABLET -> 32.dp
        DeviceType.LARGE_TABLET -> 48.dp
    }

    val verticalPadding: Dp get() = when (deviceType) {
        DeviceType.PHONE -> 16.dp
        DeviceType.LARGE_PHONE -> 20.dp
        DeviceType.TABLET -> 24.dp
        DeviceType.LARGE_TABLET -> 32.dp
    }

    val cardSpacing: Dp get() = when (deviceType) {
        DeviceType.PHONE -> 13.dp
        DeviceType.LARGE_PHONE -> 16.dp
        DeviceType.TABLET -> 20.dp
        DeviceType.LARGE_TABLET -> 24.dp
    }

    val searchBarHeight: Dp get() = when (deviceType) {
        DeviceType.PHONE -> 46.dp
        DeviceType.LARGE_PHONE -> 52.dp
        DeviceType.TABLET -> 60.dp
        DeviceType.LARGE_TABLET -> 68.dp
    }

    val categoryCardHeight: Dp get() = when (deviceType) {
        DeviceType.PHONE -> 120.dp
        DeviceType.LARGE_PHONE -> 130.dp
        DeviceType.TABLET -> 160.dp
        DeviceType.LARGE_TABLET -> 200.dp
    }
}

/**
 * Legacy Dimensions object for backward compatibility
 * This maintains the existing API while providing responsive capabilities
 */
object Dimensions {
    @Composable
    fun getResponsive(): ResponsiveDimensionValues {
        return ResponsiveDimensions.getResponsiveDimensions()
    }

    // Legacy functions that now return responsive values
    @Composable
    fun appPadding(): Dp = getResponsive().appPadding
    @Composable
    fun smallSpacer(): Dp = getResponsive().smallSpacer
    @Composable
    fun medSpacer(): Dp = getResponsive().medSpacer
    @Composable
    fun smallPadding(): Dp = getResponsive().smallPadding
    @Composable
    fun mediumPadding(): Dp = getResponsive().mediumPadding
    @Composable
    fun largePadding(): Dp = getResponsive().largePadding
    @Composable
    fun componentPadding(): Dp = getResponsive().componentPadding
    @Composable
    fun imagePadding(): Dp = getResponsive().imagePadding
    @Composable
    fun iconPadding(): Dp = getResponsive().iconPadding
    @Composable
    fun barPadding(): Dp = getResponsive().barPadding
    @Composable
    fun appLogoSize(): Dp = getResponsive().appLogoSize
    @Composable
    fun ProductSize(): Dp = getResponsive().ProductSize
    @Composable
    fun productDescSize(): Dp = getResponsive().productDescSize
    @Composable
    fun productCardSize(): Dp = getResponsive().productCardSize
    @Composable
    fun cartCardSize(): Dp = getResponsive().cartCardSize
    @Composable
    fun iconSize(): Dp = getResponsive().iconSize
    @Composable
    fun profileSize(): Dp = getResponsive().profileSize
    @Composable
    fun AnimationSize(): Dp = getResponsive().AnimationSize
    @Composable
    fun reviewImageSize(): Dp = getResponsive().reviewImageSize
    @Composable
    fun CategoryCardSize(): Dp = getResponsive().CategoryCardSize
    @Composable
    fun barProfileSize(): Dp = getResponsive().barProfileSize
    @Composable
    fun cardElevation(): Dp = getResponsive().cardElevation
    @Composable
    fun buttonElevation(): Dp = getResponsive().buttonElevation
    @Composable
    fun cardElementsPadding(): Dp = getResponsive().cardElementsPadding
    @Composable
    fun buttonPadding(): Dp = getResponsive().buttonPadding
    @Composable
    fun buttonBorder(): Dp = getResponsive().buttonBorder
    @Composable
    fun profileBoder(): Dp = getResponsive().profileBoder
    @Composable
    fun cardBorder(): Dp = getResponsive().cardBorder
}