# LabInventory - Responsive Android App

A responsive Kotlin Android application built with Jetpack Compose that adapts to different screen sizes, orientations, and device types while maintaining the existing UI style and MVVM architecture.

## 🚀 Features

### Responsive Design System
- **Multi-Screen Support**: Optimized for phones, large phones, tablets, and large tablets
- **Orientation Handling**: Automatic adaptation for portrait and landscape modes
- **Dynamic Layouts**: Grid columns, spacing, and sizing that adapt to screen dimensions
- **Responsive Typography**: Font sizes that scale appropriately for different devices
- **Adaptive Components**: UI components that resize and reposition based on screen size

### Device Support
- **Phone**: 2-column grid layout with compact spacing
- **Large Phone**: Enhanced 2-column layout with slightly larger elements
- **Tablet**: 3-column grid layout with optimized spacing
- **Large Tablet**: 4-column grid layout with generous spacing

### Architecture
- **MVVM Pattern**: Maintains existing ViewModel and Repository structure
- **Compose UI**: Modern declarative UI with responsive capabilities
- **Koin DI**: Dependency injection for clean architecture
- **Navigation**: Responsive navigation with adaptive bottom bar

## 📱 Screen Size Breakpoints

```kotlin
// Screen size breakpoints
private const val PHONE_BREAKPOINT = 600      // dp
private const val TABLET_BREAKPOINT = 840     // dp
private const val LARGE_TABLET_BREAKPOINT = 1200 // dp
```

## 🎨 Responsive Components

### 1. ResponsiveDimensions
Provides dynamic sizing based on screen size and orientation:

```kotlin
@Composable
fun MyComponent() {
    val dimensions = ResponsiveDimensions.getResponsiveDimensions()
    
    // Responsive padding
    val padding = dimensions.horizontalPadding
    
    // Responsive sizes
    val cardHeight = dimensions.categoryCardHeight
    
    // Device type detection
    val isTablet = dimensions.deviceType == DeviceType.TABLET
}
```

### 2. ResponsiveLayout
Utility functions for responsive layouts:

```kotlin
@Composable
fun MyGrid() {
    LazyVerticalGrid(
        columns = ResponsiveLayout.getGridColumns(), // 2, 3, or 4 columns
        contentPadding = ResponsiveLayout.getContentPadding(),
        horizontalArrangement = ResponsiveLayout.getGridArrangement(),
        verticalArrangement = ResponsiveLayout.getVerticalGridArrangement()
    ) {
        // Grid items
    }
}
```

### 3. ResponsiveTypography
Adaptive text sizing:

```kotlin
@Composable
fun MyText() {
    val typography = Typography.getResponsive()
    
    Text(
        text = "Hello World",
        style = typography.h1, // Responsive heading style
        fontSize = ResponsiveLayout.getResponsiveSize(20.sp, 24.sp, 28.sp)
    )
}
```

### 4. ResponsiveAppWrapper
Wrapper component for responsive layouts:

```kotlin
@Composable
fun MyScreen() {
    ResponsiveAppWrapper {
        // Your content with automatic responsive padding
    }
}
```

## 🔧 Usage Examples

### Basic Responsive Component
```kotlin
@Composable
fun ResponsiveCard() {
    Card(
        modifier = Modifier
            .height(ResponsiveLayout.getResponsiveSize(110.dp, 160.dp, 200.dp))
            .padding(ResponsiveLayout.getResponsivePadding(16.dp, 24.dp, 32.dp))
    ) {
        // Card content
    }
}
```

### Responsive Grid Layout
```kotlin
@Composable
fun ResponsiveGrid() {
    LazyVerticalGrid(
        columns = ResponsiveLayout.getGridColumns(2, 3, 4), // Custom columns
        contentPadding = ResponsiveLayout.getContentPadding(),
        horizontalArrangement = ResponsiveLayout.getGridArrangement()
    ) {
        // Grid items
    }
}
```

### Device-Specific Behavior
```kotlin
@Composable
fun AdaptiveContent() {
    ResponsiveContentWrapper(
        phoneContent = { PhoneLayout() },
        tabletContent = { TabletLayout() }
    )
}
```

### Orientation Handling
```kotlin
@Composable
fun OrientationAwareContent() {
    ResponsiveOrientationWrapper(
        portraitContent = { PortraitLayout() },
        landscapeContent = { LandscapeLayout() }
    )
}
```

## 📐 Dimension System

### Responsive Values
- **Padding**: `horizontalPadding`, `verticalPadding`, `cardSpacing`
- **Sizes**: `categoryCardHeight`, `searchBarHeight`, `iconSize`
- **Grid**: `gridColumns` (2, 3, or 4 columns)
- **Typography**: `h1`, `h2`, `h3`, `bodyMedium`, `caption`

### Size Multipliers
- **Phone**: 1.0x (base size)
- **Large Phone**: 1.1x
- **Tablet**: 1.3x
- **Large Tablet**: 1.5x

### Orientation Adjustments
- **Portrait**: 1.0x multiplier
- **Landscape**: 0.8x multiplier (reduced sizes for better fit)

## 🏗️ Project Structure

```
app/src/main/java/com/example/labinventory/
├── ui/
│   ├── theme/
│   │   ├── Dimensions.kt          # Responsive dimensions system
│   │   ├── Type.kt               # Responsive typography
│   │   ├── ResponsiveTheme.kt    # Responsive theme wrapper
│   │   └── Color.kt              # Color definitions
│   ├── components/
│   │   └── NavigationBar.kt      # Responsive navigation
│   └── screens/
│       ├── HomeScreen.kt         # Responsive home screen
│       └── EquipmentScreen.kt    # Responsive equipment screen
├── util/
│   ├── ResponsiveLayout.kt       # Layout utilities
│   ├── ResponsiveAppWrapper.kt   # Responsive wrapper component
│   └── PxToDp.kt                # Legacy conversion utility
└── MainActivity.kt               # Main activity with responsive theme
```

## 🚀 Getting Started

[//]: # ()
[//]: # (### 1. Use Responsive Dimensions)

[//]: # (```kotlin)

[//]: # (import com.example.labinventory.util.ResponsiveLayout)

[//]: # ()
[//]: # (@Composable)

[//]: # (fun MyComponent&#40;&#41; {)

[//]: # (    val padding = ResponsiveLayout.getHorizontalPadding&#40;&#41;)

[//]: # (    val isTablet = ResponsiveLayout.isTablet&#40;&#41;)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### 2. Wrap Content with ResponsiveAppWrapper)

[//]: # (```kotlin)

[//]: # (import com.example.labinventory.util.ResponsiveAppWrapper)

[//]: # ()
[//]: # (@Composable)

[//]: # (fun MyScreen&#40;&#41; {)

[//]: # (    ResponsiveAppWrapper {)

[//]: # (        // Your content here)

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### 3. Use Responsive Grid Layouts)

[//]: # (```kotlin)

[//]: # (LazyVerticalGrid&#40;)

[//]: # (    columns = ResponsiveLayout.getGridColumns&#40;&#41;,)

[//]: # (    contentPadding = ResponsiveLayout.getContentPadding&#40;&#41;)

[//]: # (&#41; {)

[//]: # (    // Grid items)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### 4. Apply Responsive Typography)

[//]: # (```kotlin)

[//]: # (import com.example.labinventory.ui.theme.Typography)

[//]: # ()
[//]: # (@Composable)

[//]: # (fun MyText&#40;&#41; {)

[//]: # (    Text&#40;)

[//]: # (        text = "Title",)

[//]: # (        style = Typography.h1 // Responsive heading)

[//]: # (    &#41;)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (## 🔄 Configuration Changes)

[//]: # ()
[//]: # (The app automatically handles:)

[//]: # (- **Screen rotation** &#40;portrait ↔ landscape&#41;)

[//]: # (- **Screen size changes** &#40;resizable windows, foldable devices&#41;)

[//]: # (- **Density changes** &#40;different pixel densities&#41;)

[//]: # (- **Font scale changes** &#40;accessibility settings&#41;)

[//]: # ()
[//]: # (## 📱 Testing)

[//]: # ()
[//]: # (### Test Different Screen Sizes)

[//]: # (1. Use Android Studio's Layout Inspector)

[//]: # (2. Test on different device emulators)

[//]: # (3. Use foldable device emulators)

[//]: # (4. Test orientation changes)

[//]: # ()
[//]: # (### Test Responsive Behavior)

[//]: # (```kotlin)

[//]: # (// Check device type)

[//]: # (val isTablet = ResponsiveLayout.isTablet&#40;&#41;)

[//]: # (val isLandscape = ResponsiveLayout.isLandscape&#40;&#41;)

[//]: # ()
[//]: # (// Get responsive values)

[//]: # (val padding = ResponsiveLayout.getHorizontalPadding&#40;&#41;)

[//]: # (val columns = ResponsiveLayout.getGridColumns&#40;&#41;)

[//]: # (```)

[//]: # ()
[//]: # (## 🎯 Best Practices)

[//]: # ()
[//]: # (1. **Always use responsive dimensions** instead of hardcoded values)

[//]: # (2. **Wrap screens with ResponsiveAppWrapper** for consistent padding)

[//]: # (3. **Use ResponsiveLayout utilities** for grids and spacing)

[//]: # (4. **Test on multiple screen sizes** and orientations)

[//]: # (5. **Maintain existing UI style** while adding responsiveness)

[//]: # ()
[//]: # (## 🔧 Customization)

[//]: # ()
[//]: # (### Custom Breakpoints)

[//]: # (```kotlin)

[//]: # (// In ResponsiveDimensions.kt)

[//]: # (private const val CUSTOM_BREAKPOINT = 700 // dp)

[//]: # (```)

[//]: # ()
[//]: # (### Custom Multipliers)

[//]: # (```kotlin)

[//]: # (// In ResponsiveDimensions.kt)

[//]: # (val sizeMultiplier = when &#40;deviceType&#41; {)

[//]: # (    DeviceType.PHONE -> 1.0f)

[//]: # (    DeviceType.TABLET -> 1.4f // Custom multiplier)

[//]: # (    // ...)

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (### Custom Grid Columns)

[//]: # (```kotlin)

[//]: # (// In ResponsiveLayout.kt)

[//]: # (fun getCustomGridColumns&#40;phone: Int, tablet: Int&#41;: GridCells {)

[//]: # (    return when &#40;deviceType&#41; {)

[//]: # (        DeviceType.PHONE -> GridCells.Fixed&#40;phone&#41;)

[//]: # (        DeviceType.TABLET -> GridCells.Fixed&#40;tablet&#41;)

[//]: # (        // ...)

[//]: # (    })

[//]: # (})

[//]: # (```)

[//]: # ()
[//]: # (## 📚 Dependencies)

[//]: # ()
[//]: # (The responsive system uses:)

[//]: # (- **Jetpack Compose**: Modern UI toolkit)

[//]: # (- **Material3**: Material Design components)

[//]: # (- **AndroidX**: Core Android libraries)

[//]: # (- **Koin**: Dependency injection)

[//]: # ()
[//]: # (## 🤝 Contributing)

[//]: # ()
[//]: # (When adding new components:)

[//]: # (1. Use responsive dimensions instead of hardcoded values)

[//]: # (2. Test on different screen sizes)

[//]: # (3. Maintain existing UI style)

[//]: # (4. Follow MVVM architecture)

[//]: # (5. Add responsive documentation)

[//]: # ()
[//]: # (## 📄 License)

[//]: # ()
[//]: # (This project maintains the existing license while adding responsive capabilities.)

[//]: # ()
[//]: # (---)

[//]: # ()
[//]: # (**Note**: This responsive system maintains 100% backward compatibility with existing code while providing new responsive features. All existing hardcoded values are preserved as defaults and automatically scaled based on screen size and orientation.)
