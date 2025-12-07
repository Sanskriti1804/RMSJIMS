package com.example.rmsjims.ui.screens.shared

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.data.local.RememberMeManager
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppIconTextField
import com.example.rmsjims.ui.components.AppLogoImage
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.theme.app_background
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
){

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var rememberMe by remember { mutableStateOf(false) }
    var autoLoginRequested by remember { mutableStateOf(false) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val authViewModel: AuthViewModel = koinViewModel()
    val authUiState by authViewModel.uiState.collectAsState()
    val rememberMeManager = remember(context) { RememberMeManager(context) }
    val adminEmail = stringResource(R.string.admin_email)

    LaunchedEffect(Unit) {
        val rememberEnabled = rememberMeManager.isRememberMeEnabled()
        rememberMe = rememberEnabled
        if (rememberEnabled) {
            rememberMeManager.getSavedCredentials()?.let { saved ->
                username = saved.username
                password = saved.password
                autoLoginRequested = true
            }
        }
    }

    LaunchedEffect(autoLoginRequested) {
        if (autoLoginRequested && rememberMe && username.isNotBlank() && password.isNotBlank()) {
            navController.navigate(Screen.RoleSelectionScreen.route) {
                popUpTo(Screen.LoginScreen.route) { inclusive = true }
            }
            autoLoginRequested = false
        }
    }

    if (showForgotPasswordDialog) {
        Dialog(onDismissRequest = { showForgotPasswordDialog = false }) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
                shape = RoundedCornerShape(4.dp),
                backgroundColor = whiteColor,
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .padding(
                            vertical = ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp),
                            horizontal = ResponsiveLayout.getResponsiveSize(18.dp, 22.dp, 26.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CustomLabel(
                        header = "Password Assistance",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                        headerColor = onSurfaceColor
                    )
                    Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))
                    CustomLabel(
                        header = "Contact the administrator to recover your account.",
                        fontSize = 14.sp,
                        headerColor = onSurfaceColor.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(18.dp, 22.dp, 26.dp)))
                    AppButton(
                        onClick = {
                            val dummyEmail = "admin@jims.edu"
                            val intent = Intent(
                                Intent.ACTION_SENDTO,
                                Uri.parse("mailto:$dummyEmail")
                            ).apply {
                                putExtra(Intent.EXTRA_SUBJECT, "Password Reset Request")
                                putExtra(
                                    Intent.EXTRA_TEXT,
                                    "Hello Administrator,\n\nI am unable to locate my login credentials. Could you please assist with resetting my password?\n\nThank you."
                                )
                            }

                            if (intent.resolveActivity(context.packageManager) != null) {
                                context.startActivity(intent)
                            }
                            showForgotPasswordDialog = false
                        },
                        buttonText = "Contact Administrator"
                    )
                }
            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(app_background)
            .padding(ResponsiveLayout.getHorizontalPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(22.dp, 32.dp, 42.dp)))
        AppLogoImage()
        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(4.dp, 8.dp, 12.dp)))

        AppIconTextField(
            value = username,
            onValueChange = {username = it},
            placeholder = "Username",
            icon = painterResource(R.drawable.ic_user),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ResponsiveLayout.getResponsiveSize(2.dp, 2.5.dp, 3.dp)),
            shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(10.dp, 14.dp, 18.dp)))

        AppIconTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = "Password",
            visualTransformation = PasswordVisualTransformation(),
            icon = painterResource((R.drawable.ic_password)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = ResponsiveLayout.getResponsiveSize(2.dp, 2.5.dp, 3.dp)),
            shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))

        CustomLabel(
            header = "Forgot your username or password?",
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor.copy(0.5f),
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    onClick = {
                        showForgotPasswordDialog = true
                    }
                )
        )
        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomLabel(
                header = "Remember Me",
                headerColor = onSurfaceColor.copy(alpha = 0.8f),
                fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
            )
            Spacer(modifier = Modifier.width(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp)))
            Switch(
                checked = rememberMe,
                onCheckedChange = { isChecked ->
                    rememberMe = isChecked
                    if (isChecked) {
                        rememberMeManager.setRememberMe(true)
                    } else {
                        rememberMeManager.clearCredentials()
                    }
                },
                modifier = Modifier.scale(0.85f),
                colors = SwitchDefaults.colors(
                    checkedThumbColor = whiteColor,
                    checkedTrackColor = primaryColor,
                    uncheckedThumbColor = whiteColor,
                    uncheckedTrackColor = onSurfaceColor.copy(alpha = 0.3f)
                )
            )
        }

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)))

        AppButton(
            onClick = {
                if (rememberMe && username.isNotBlank() && password.isNotBlank()) {
                    rememberMeManager.saveCredentials(username, password, true)
                } else {
                    rememberMeManager.clearCredentials()
                }

                // Trigger Supabase email/password sign-in without changing navigation flow
                if (username.isNotBlank() && password.isNotBlank()) {
                    authViewModel.signInWithEmail(
                        email = username,
                        password = password
                    )
                }

                navController.navigate(Screen.RoleSelectionScreen.route) {
                    popUpTo(Screen.LoginScreen.route) { inclusive = true }
                }
            },
            buttonText = "LOGIN"
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(18.dp, 24.dp, 30.dp)))

        CustomLabel(
            header = "Or continue with",
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor.copy(alpha = 0.6f),
            modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsiveSize(2.dp, 4.dp, 6.dp))
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(10.dp, 14.dp, 18.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SocialLoginOption(
                label = "Sign in with Google",
                iconRes = R.drawable.ic_google,
                modifier = Modifier.weight(1f),
                onClick = { authViewModel.signInWithGoogle() }
            )
            SocialLoginOption(
                label = "Sign in with GitHub",
                iconRes = R.drawable.ic_github,
                modifier = Modifier.weight(1f),
                onClick = { authViewModel.signInWithGitHub() }
            )
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    val dummyNavController = rememberNavController()
//    LoginScreen(dummyNavController)
//}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun SocialLoginOption(
    label: String,
    iconRes: Int,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(14.dp, 18.dp, 22.dp)),
        backgroundColor = whiteColor,
        onClick = onClick,
        elevation = 0.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = ResponsiveLayout.getResponsiveSize(12.dp, 14.dp, 16.dp),
                    horizontal = ResponsiveLayout.getResponsiveSize(14.dp, 18.dp, 22.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(22.dp, 28.dp, 32.dp)),
                colorFilter = ColorFilter.tint(primaryColor)
            )
        }
    }
}