package com.example.rmsjims.ui.screens.shared

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import kotlinx.coroutines.delay

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AboutAppScreen(
    navController: NavHostController
) {
    var showFabIcon by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        delay(15000) // 15 seconds
        showFabIcon = true
    }
    
    Scaffold(
        containerColor = primaryColor,
        floatingActionButton = {
            AnimatedContent(
                targetState = showFabIcon,
                transitionSpec = {
                    fadeIn(tween(500)) + scaleIn() with fadeOut(tween(500)) + scaleOut()
                },
                label = "FAB animation"
            ) { isIconState ->
                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screen.RoleSelectionScreen.route)
                    },
                    modifier = if (isIconState) {
                        Modifier.wrapContentSize()
                    } else {
                        val horizontalPadding = ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp)
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = horizontalPadding)
                    },
                    containerColor = whiteColor,
                    contentColor = primaryColor,
                    shape = if (isIconState) CircleShape else RoundedCornerShape(ResponsiveLayout.getResponsiveSize(30.dp, 36.dp, 42.dp))
                ) {
                    if (isIconState) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Go to app",
                            modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp))
                        )
                    } else {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Go to the app",
                                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                                color = primaryColor,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        horizontal = ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp),
                        vertical = ResponsiveLayout.getResponsiveSize(20.dp, 24.dp, 28.dp)
                    )
            ) {
                // Top section with logo and location
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Logo and location in top right
                    Column(
                        modifier = Modifier.align(Alignment.TopEnd),
                        horizontalAlignment = Alignment.End
                    ) {
                        // Logo at the top right
                        Image(
                            painter = painterResource(R.drawable.jims_logo),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .size(ResponsiveLayout.getResponsiveSize(160.dp, 180.dp, 200.dp)),
                            colorFilter = ColorFilter.tint(whiteColor)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Spacer(modifier = Modifier.weight(1f))
                
                // Bottom text with wrapping and line spacing
                Text(
                    text = "A centralized platform for managing lab equipment, bookings, and technical resources across JIMS campus",
                    color = whiteColor,
                    style = TextStyle(
                        fontSize = 38.sp,
                        fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                        fontWeight = FontWeight.Normal,
                        lineHeight = 50.sp
                    ),
                    modifier = Modifier.padding(bottom = ResponsiveLayout.getResponsiveSize(100.dp, 110.dp, 120.dp)),
                    maxLines = 10
                )
            }
        }
    }
}

