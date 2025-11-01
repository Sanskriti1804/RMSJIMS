package com.example.rmsjims.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppSearchBar
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.ProfileImage
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.selectedchipColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun UserManagementScreen(
    navController: NavHostController
) {
    // Placeholder users data
    val users = listOf(
        User(
            name = "Dr. Ravi Kumar",
            email = "ravik@edu.com",
            role = "Faculty",
            department = "Computer Science",
            status = "Active"
        ),
        User(
            name = "Prof. Meera Sharma",
            email = "meeras@edu.com",
            role = "Faculty",
            department = "Electronics",
            status = "Active"
        ),
        User(
            name = "Akash Singh",
            email = "akashs@edu.com",
            role = "Lab Assistant",
            department = "Mechanical",
            status = "Active"
        ),
        User(
            name = "Dr. Amit Patel",
            email = "amitp@edu.com",
            role = "Faculty",
            department = "Mechanical",
            status = "Inactive"
        ),
        User(
            name = "Sunita Reddy",
            email = "sunitar@edu.com",
            role = "Admin",
            department = "Physics",
            status = "Active"
        )
    )
    
    var searchQuery by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("All") }
    val roles = listOf("All", "Faculty", "Lab Assistant", "Admin", "Student")

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "User Management",
                onNavigationClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Search Bar
            AppSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding())
                    .height(ResponsiveLayout.getResponsiveSize(46.dp, 60.dp, 68.dp))
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Role Filter Chips
            androidx.compose.foundation.lazy.LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = ResponsiveLayout.getHorizontalPadding()),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                items(roles) { role ->
                    RoleChip(
                        role = role,
                        isSelected = selectedRole == role,
                        onClick = { selectedRole = role }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Users List
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                items(users) { user ->
                    UserCard(user = user)
                }
            }
        }
    }
}

@Composable
fun RoleChip(
    role: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .background(
                color = if (isSelected) selectedchipColor else onSurfaceVariant,
                shape = RoundedCornerShape(pxToDp(20))
            )
            .clickable { onClick() }
            .padding(
                horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                vertical = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp)
            )
    ) {
        CustomLabel(
            header = role,
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = if (isSelected) primaryColor else onSurfaceColor.copy(0.7f)
        )
    }
}

@Composable
fun UserCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            horizontalArrangement = Arrangement.spacedBy(pxToDp(16)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage(
                size = ResponsiveLayout.getResponsiveSize(50.dp, 60.dp, 70.dp)
            )
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(pxToDp(4))
            ) {
                CustomLabel(
                    header = user.name,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                    headerColor = onSurfaceColor
                )
                CustomLabel(
                    header = user.email,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(pxToDp(8))
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = primaryColor.copy(0.2f),
                                shape = RoundedCornerShape(pxToDp(12))
                            )
                            .padding(
                                horizontal = pxToDp(8),
                                vertical = pxToDp(4)
                            )
                    ) {
                        CustomLabel(
                            header = user.role,
                            fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
                            headerColor = primaryColor
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(
                                color = if (user.status == "Active") 
                                    Color(0xFF26BB64C).copy(0.2f) 
                                else 
                                    Color(0xFFE64646).copy(0.2f),
                                shape = RoundedCornerShape(pxToDp(12))
                            )
                            .padding(
                                horizontal = pxToDp(8),
                                vertical = pxToDp(4)
                            )
                    ) {
                        CustomLabel(
                            header = user.status,
                            fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
                            headerColor = if (user.status == "Active") 
                                Color(0xFF26BB64C) 
                            else 
                                Color(0xFFE64646)
                        )
                    }
                }
                CustomLabel(
                    header = user.department,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
            }
            
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                AppButton(
                    buttonText = "Edit",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth()
                )
                AppButton(
                    buttonText = "Delete",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = cardColor,
                    contentColor = onSurfaceColor
                )
            }
        }
    }
}

// Placeholder data class
data class User(
    val name: String,
    val email: String,
    val role: String,
    val department: String,
    val status: String
)

@Preview(showBackground = true)
@Composable
fun UserManagementScreenPreview() {
    val navController = rememberNavController()
    UserManagementScreen(navController = navController)
}
