package com.example.labinventory.data.remote

import androidx.compose.runtime.staticCompositionLocalOf
import com.example.labinventory.data.model.ResponsiveLayout

//allows to share screen size and layout info across your entire Compose app, and it shows an error if you forget to set it at the top of your UI
val LocalResponsiveLayout = staticCompositionLocalOf<ResponsiveLayout> {
    error("No ResposniveLayout provided")
}