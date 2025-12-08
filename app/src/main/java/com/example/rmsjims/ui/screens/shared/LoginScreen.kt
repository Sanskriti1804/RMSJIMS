package com.example.rmsjims.ui.screens.shared

import android.content.Intent
import android.net.Uri
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppIconTextField
import com.example.rmsjims.ui.components.AppLogoImage
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.theme.app_background
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.repository.UsersRepository
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.AuthViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
    parentNavController: NavHostController? = null
){

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authViewModel: AuthViewModel = koinViewModel()
    val authUiState by authViewModel.uiState.collectAsState()
    val adminEmail = stringResource(R.string.admin_email)
    val sessionViewModel: UserSessionViewModel = koinViewModel()
    val usersRepository: UsersRepository = koinInject()
    val coroutineScope = rememberCoroutineScope()

    if (showErrorDialog) {
        Dialog(onDismissRequest = { showErrorDialog = false }) {
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
                        header = "Login Error",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                        headerColor = onSurfaceColor
                    )
                    Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))
                    CustomLabel(
                        header = errorMessage,
                        fontSize = 14.sp,
                        headerColor = onSurfaceColor.copy(alpha = 0.8f)
                    )
                    Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(18.dp, 22.dp, 26.dp)))
                    AppButton(
                        onClick = { showErrorDialog = false },
                        buttonText = "OK"
                    )
                }
            }
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

        AppButton(
            onClick = {
                // Validate inputs
                if (username.isBlank() || password.isBlank()) {
                    errorMessage = "Please enter both email and password"
                    showErrorDialog = true
                    return@AppButton
                }

                // Authenticate with database
                val email = username.trim().lowercase()
                coroutineScope.launch {
                    val user = usersRepository.authenticateUser(email, password)
                    
                    if (user == null) {
                        errorMessage = "Invalid email or password. Please try again."
                        showErrorDialog = true
                        return@launch
                    }

                    // Validate email domain matches role from database
                    val expectedDomain = when (user.role.lowercase()) {
                        "admin" -> "@admin.com"
                        "staff" -> "@staff.com"
                        "assistant" -> "@assistant.com"
                        else -> null
                    }
                    
                    if (expectedDomain != null && !email.endsWith(expectedDomain)) {
                        errorMessage = "Email domain does not match user role. Expected $expectedDomain"
                        showErrorDialog = true
                        return@launch
                    }

                    // Determine role from database
                    val determinedRole = when (user.role.lowercase()) {
                        "admin" -> UserRole.ADMIN
                        "staff" -> UserRole.STAFF
                        "assistant" -> UserRole.ASSISTANT
                        else -> UserRole.UNASSIGNED
                    }

                    // Save the determined role
                    if (determinedRole != UserRole.UNASSIGNED) {
                        sessionViewModel.updateRole(determinedRole)
                    }

                    // Trigger Supabase email/password sign-in (for future use)
                    authViewModel.signInWithEmail(
                        email = username,
                        password = password
                    )

                    // Navigate to role-specific graph based on determined role
                    val targetNavController = parentNavController ?: navController
                    when (determinedRole) {
                        UserRole.ADMIN -> {
                            targetNavController.navigate(Screen.AdminNavGraph.route) {
                                popUpTo(Screen.SharedNavGraph.route) { inclusive = true }
                            }
                        }
                        UserRole.ASSISTANT -> {
                            targetNavController.navigate(Screen.AssistantNavGraph.route) {
                                popUpTo(Screen.SharedNavGraph.route) { inclusive = true }
                            }
                        }
                        UserRole.STAFF -> {
                            targetNavController.navigate(Screen.StaffNavGraph.route) {
                                popUpTo(Screen.SharedNavGraph.route) { inclusive = true }
                            }
                        }
                        else -> {
                            // If role cannot be determined, show error
                            errorMessage = "Invalid user role. Please contact administrator."
                            showErrorDialog = true
                        }
                    }
                }
            },
            buttonText = "LOGIN"
        )

    }
}
