package com.example.rmsjims.ui.screens.admin

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.navigation.Screen
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.example.rmsjims.R
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppIconTextField
import com.example.rmsjims.ui.components.AppSearchBar
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.ProfileImage
import com.example.rmsjims.ui.components.FilteredAppTextField
import com.example.rmsjims.repository.UsersRepository
import com.example.rmsjims.data.schema.Users
import org.koin.compose.koinInject
import androidx.compose.ui.text.input.PasswordVisualTransformation
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.chipColor
import com.example.rmsjims.ui.theme.errorColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.selectedChipTextColor
import com.example.rmsjims.ui.theme.selectedchipColor
import com.example.rmsjims.ui.theme.successColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun UserManagementScreen(
    navController: NavHostController
) {
    val usersRepository: UsersRepository = koinInject()
    val coroutineScope = rememberCoroutineScope()
    
    var showAddUserSheet by remember { mutableStateOf(false) }
    var newUserEmail by remember { mutableStateOf("") }
    var newUserPassword by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var dbUsers by remember { mutableStateOf<List<Users>>(emptyList()) }
    var isLoadingUsers by remember { mutableStateOf(false) }
    
    // Fetch users from database
    LaunchedEffect(Unit) {
        isLoadingUsers = true
        try {
            dbUsers = usersRepository.getAllUsers()
        } catch (e: Exception) {
            // Handle error
        } finally {
            isLoadingUsers = false
        }
    }
    
    // Placeholder users data (for display purposes - can be removed later)
    val placeholderUsers = listOf(
        User(
            id = "1",
            name = "Dr. Ravi Kumar",
            email = "ravik@edu.com",
            phone = "+91 9876543210",
            role = "Staff",
            building = "Building A",
            department = "Computer Science",
            class_ = null,
            status = UserStatus.ACTIVE,
            activeAssignmentsCount = 3,
            overdueItemsCount = 1,
            joinDate = "2023-01-15"
        ),
        User(
            id = "2",
            name = "Prof. Meera Sharma",
            email = "meeras@edu.com",
            phone = "+91 9876543211",
            role = "Staff",
            building = "Building B",
            department = "Electronics",
            class_ = null,
            status = UserStatus.ACTIVE,
            activeAssignmentsCount = 2,
            overdueItemsCount = 0,
            joinDate = "2023-02-20"
        ),
        User(
            id = "3",
            name = "Akash Singh",
            email = "akashs@edu.com",
            phone = "+91 9876543212",
            role = "Lab Assistant",
            building = "Building C",
            department = "Mechanical",
            class_ = null,
            status = UserStatus.ACTIVE,
            activeAssignmentsCount = 5,
            overdueItemsCount = 2,
            joinDate = "2023-03-10"
        ),
        User(
            id = "4",
            name = "Dr. Amit Patel",
            email = "amitp@edu.com",
            phone = "+91 9876543213",
            role = "Staff",
            building = "Building A",
            department = "Mechanical",
            class_ = null,
            status = UserStatus.SUSPENDED,
            activeAssignmentsCount = 0,
            overdueItemsCount = 0,
            joinDate = "2022-11-05"
        ),
        User(
            id = "5",
            name = "Sunita Reddy",
            email = "sunitar@edu.com",
            phone = "+91 9876543214",
            role = "Admin",
            building = "Building A",
            department = "Physics",
            class_ = null,
            status = UserStatus.ACTIVE,
            activeAssignmentsCount = 1,
            overdueItemsCount = 0,
            joinDate = "2022-08-12"
        ),
        User(
            id = "6",
            name = "Rajesh Kumar",
            email = "rajeshk@edu.com",
            phone = "+91 9876543215",
            role = "Student",
            building = "Building B",
            department = "Computer Science",
            class_ = "CS-2024",
            status = UserStatus.ACTIVE,
            activeAssignmentsCount = 2,
            overdueItemsCount = 1,
            joinDate = "2024-01-08"
        )
    )
    
    var searchQuery by remember { mutableStateOf("") }
    var showFilterSheet by remember { mutableStateOf(false) }
    var selectedMainFilter by remember { mutableStateOf<UserMainFilter?>(null) }
    var selectedSubFilters by remember { mutableStateOf<Set<String>>(emptySet()) }
    
    // Role options for dropdown
    val roleOptions = listOf("admin", "staff", "assistant")
    
    // Helper function to get expected email domain for role
    fun getExpectedDomain(role: String): String {
        return when (role.lowercase()) {
            "admin" -> "@admin.com"
            "staff" -> "@staff.com"
            "assistant" -> "@assistant.com"
            else -> ""
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "User Management",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showAddUserSheet = true },
                containerColor = primaryColor,
                shape = CircleShape,
                modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(56.dp, 64.dp, 72.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add User",
                    tint = whiteColor,
                    modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp))
                )
            }
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Search Bar and Filter Button
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    modifier = Modifier
                        .weight(1f)
                        .height(ResponsiveLayout.getResponsiveSize(46.dp, 60.dp, 68.dp))
                )
                IconButton(
                    onClick = { showFilterSheet = true },
                    modifier = Modifier
                        .size(ResponsiveLayout.getResponsiveSize(46.dp, 60.dp, 68.dp))
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_vector),
                        contentDescription = "Filter",
                        tint = primaryColor
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
                // Show database users if available, otherwise show placeholder
                if (dbUsers.isNotEmpty()) {
                    items(dbUsers) { dbUser ->
                        // Convert Users to User for display
                        val displayUser = User(
                            id = dbUser.id?.toString() ?: "0",
                            name = dbUser.email.split("@").firstOrNull()?.replaceFirstChar { it.uppercaseChar() } ?: "User",
                            email = dbUser.email,
                            phone = "",
                            role = dbUser.role.replaceFirstChar { it.uppercaseChar() },
                            building = "",
                            department = "",
                            class_ = null,
                            status = UserStatus.ACTIVE,
                            activeAssignmentsCount = 0,
                            overdueItemsCount = 0,
                            joinDate = dbUser.createdAt ?: ""
                        )
                        UserCard(
                            user = displayUser,
                            onClick = {
                                navController.navigate(Screen.UserDetailScreen.createRoute(displayUser.id))
                            }
                        )
                    }
                } else if (!isLoadingUsers) {
                    // Show placeholder users if no database users
                    items(placeholderUsers) { user ->
                        UserCard(
                            user = user,
                            onClick = {
                                navController.navigate(Screen.UserDetailScreen.createRoute(user.id))
                            }
                        )
                    }
                }
            }
        }
        
        // Filter Bottom Sheet
        if (showFilterSheet) {
            UserFilterBottomSheet(
                onDismiss = { showFilterSheet = false },
                selectedMainFilter = selectedMainFilter,
                onMainFilterSelected = { selectedMainFilter = it },
                selectedSubFilters = selectedSubFilters,
                onSubFiltersChanged = { selectedSubFilters = it }
            )
        }
        
        // Add User Bottom Sheet
        if (showAddUserSheet) {
            AddUserBottomSheet(
                onDismiss = { 
                    showAddUserSheet = false
                    newUserEmail = ""
                    newUserPassword = ""
                    selectedRole = ""
                    errorMessage = ""
                },
                email = newUserEmail,
                onEmailChange = { 
                    newUserEmail = it
                    errorMessage = ""
                },
                password = newUserPassword,
                onPasswordChange = { 
                    newUserPassword = it
                    errorMessage = ""
                },
                selectedRole = selectedRole,
                onRoleSelected = { 
                    selectedRole = it
                    errorMessage = ""
                    // Auto-append domain to email if it doesn't match
                    val expectedDomain = getExpectedDomain(it)
                    if (newUserEmail.isNotEmpty() && !newUserEmail.endsWith(expectedDomain)) {
                        // Remove any existing domain and add correct one
                        val emailWithoutDomain = newUserEmail.split("@").firstOrNull() ?: ""
                        if (emailWithoutDomain.isNotEmpty()) {
                            newUserEmail = "$emailWithoutDomain$expectedDomain"
                        }
                    }
                },
                roleOptions = roleOptions,
                expectedDomain = getExpectedDomain(selectedRole),
                errorMessage = errorMessage,
                usersRepository = usersRepository,
                onUserAdded = { success ->
                    if (success) {
                        showAddUserSheet = false
                        showSuccessDialog = true
                        newUserEmail = ""
                        newUserPassword = ""
                        selectedRole = ""
                        errorMessage = ""
                        // Refresh users list
                        coroutineScope.launch {
                            dbUsers = usersRepository.getAllUsers()
                        }
                    }
                },
                onError = { message ->
                    errorMessage = message
                }
            )
        }
        
        // Success Dialog
        if (showSuccessDialog) {
            Dialog(onDismissRequest = { showSuccessDialog = false }) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.85f)
                        .padding(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
                    shape = RoundedCornerShape(4.dp),
                    colors = CardDefaults.cardColors(containerColor = whiteColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
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
                            header = "Success",
                            fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                            headerColor = successColor
                        )
                        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))
                        CustomLabel(
                            header = "User added successfully",
                            fontSize = 14.sp,
                            headerColor = onSurfaceColor.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(18.dp, 22.dp, 26.dp)))
                        AppButton(
                            onClick = { showSuccessDialog = false },
                            buttonText = "OK"
                        )
                    }
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
fun UserCard(
    user: User,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(defaultElevation = pxToDp(1))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                    
                    // Building / Department / Class
                    val locationText = when {
                        user.class_ != null -> "${user.department} - ${user.class_}"
                        user.department.isNotEmpty() -> "${user.building} - ${user.department}"
                        else -> user.building
                    }
                    CustomLabel(
                        header = locationText,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.6f)
                    )
                    
                    // Role and Status Tags
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(pxToDp(8)),
                        verticalAlignment = Alignment.CenterVertically
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
                        StatusTag(status = user.status)
                    }
                }
            }
            
            // Assignment Indicators
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(16))
            ) {
                // Active Assignments
                if (user.activeAssignmentsCount > 0) {
                    AssignmentIndicator(
                        label = "Active",
                        count = user.activeAssignmentsCount,
                        color = primaryColor
                    )
                }
                
                // Overdue Items
                if (user.overdueItemsCount > 0) {
                    AssignmentIndicator(
                        label = "Overdue",
                        count = user.overdueItemsCount,
                        color = errorColor
                    )
                }
            }
            
            // View Details Button
            AppButton(
                buttonText = "View Details",
                onClick = onClick,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun StatusTag(status: UserStatus) {
    val (bgColor, textColor) = when (status) {
        UserStatus.ACTIVE -> successColor.copy(0.2f) to successColor
        UserStatus.SUSPENDED -> errorColor.copy(0.2f) to errorColor
        UserStatus.PENDING -> Color(0xFFFFA500).copy(0.2f) to Color(0xFFFFA500)
        UserStatus.OVERDUE -> errorColor.copy(0.2f) to errorColor
        UserStatus.DISABLED -> onSurfaceColor.copy(0.2f) to onSurfaceColor.copy(0.7f)
    }
    
    Box(
        modifier = Modifier
            .background(
                color = bgColor,
                shape = RoundedCornerShape(pxToDp(12))
            )
            .padding(
                horizontal = pxToDp(8),
                vertical = pxToDp(4)
            )
    ) {
        CustomLabel(
            header = status.displayName,
            fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
            headerColor = textColor
        )
    }
}

@Composable
fun AssignmentIndicator(
    label: String,
    count: Int,
    color: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(pxToDp(4)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(pxToDp(8))
                .background(color = color, shape = RoundedCornerShape(50))
        )
        CustomLabel(
            header = "$label: $count",
            fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
            headerColor = onSurfaceColor.copy(0.7f)
        )
    }
}

// Data classes
data class User(
    val id: String,
    val name: String,
    val email: String,
    val phone: String,
    val role: String, // Student, Staff, Lab Assistant, Admin
    val building: String,
    val department: String,
    val class_: String?, // For students
    val status: UserStatus,
    val activeAssignmentsCount: Int,
    val overdueItemsCount: Int,
    val joinDate: String
)

enum class UserStatus(val displayName: String) {
    ACTIVE("Active"),
    SUSPENDED("Suspended"),
    PENDING("Pending"),
    OVERDUE("Overdue"),
    DISABLED("Disabled")
}

enum class UserMainFilter {
    BUILDINGS,
    CURRENT_USERS,
    ALL_USERS
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserFilterBottomSheet(
    onDismiss: () -> Unit,
    selectedMainFilter: UserMainFilter?,
    onMainFilterSelected: (UserMainFilter?) -> Unit,
    selectedSubFilters: Set<String>,
    onSubFiltersChanged: (Set<String>) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        sheetState.show()
    }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 16.dp)),
        containerColor = whiteColor,
        dragHandle = null,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getHorizontalPadding())
        ) {
            CustomLabel(
                header = "Filter Users",
                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                headerColor = onSurfaceColor,
                modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp))
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 16.dp)))
            
            // Main Filters
            CustomLabel(
                header = "Main Filter",
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                headerColor = selectedChipTextColor,
                modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsivePadding(14.dp, 16.dp, 20.dp))
            )
            
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                UserMainFilter.values().forEach { filter ->
                    FilterChipItem(
                        label = filter.name.replace("_", " "),
                        isSelected = selectedMainFilter == filter,
                        onClick = {
                            onMainFilterSelected(if (selectedMainFilter == filter) null else filter)
                            onSubFiltersChanged(emptySet())
                        }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)))
            
            // Sub-Filters (dependent on main filter)
            if (selectedMainFilter != null) {
                CustomLabel(
                    header = when (selectedMainFilter) {
                        UserMainFilter.BUILDINGS -> "Sub-Filter"
                        UserMainFilter.CURRENT_USERS -> "Sub-Filter"
                        UserMainFilter.ALL_USERS -> "Role Filter"
                    },
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = selectedChipTextColor,
                    modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsivePadding(14.dp, 16.dp, 20.dp))
                )
                
                val subFilterOptions = when (selectedMainFilter) {
                    UserMainFilter.BUILDINGS -> listOf("Departments", "Classes", "Floors")
                    UserMainFilter.CURRENT_USERS -> listOf("Users with overdue items", "Active assignments", "Multiple open tasks")
                    UserMainFilter.ALL_USERS -> listOf("Student", "Staff", "Admin", "Lab Assistant")
                }
                
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    subFilterOptions.forEach { option ->
                        FilterChipItem(
                            label = option,
                            isSelected = selectedSubFilters.contains(option),
                            onClick = {
                                val newSet = if (selectedSubFilters.contains(option)) {
                                    selectedSubFilters - option
                                } else {
                                    selectedSubFilters + option
                                }
                                onSubFiltersChanged(newSet)
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Bottom Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppButton(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    buttonText = "Reset",
                    containerColor = cardColor,
                    contentColor = onSurfaceColor,
                    onClick = {
                        onMainFilterSelected(null)
                        onSubFiltersChanged(emptySet())
                    }
                )
                AppButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Apply Filter",
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
        }
    }
}

@Composable
fun FilterChipItem(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(pxToDp(4)),
        color = if (isSelected) selectedchipColor else Color.Transparent,
        border = BorderStroke(
            pxToDp(1),
            if (isSelected) primaryColor else chipColor
        ),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            color = selectedChipTextColor,
            modifier = Modifier.padding(
                horizontal = pxToDp(9),
                vertical = pxToDp(6)
            ),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.font_alliance_regular_two))
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserBottomSheet(
    onDismiss: () -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    selectedRole: String,
    onRoleSelected: (String) -> Unit,
    roleOptions: List<String>,
    expectedDomain: String,
    errorMessage: String,
    usersRepository: UsersRepository,
    onUserAdded: (Boolean) -> Unit,
    onError: (String) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()
    
    LaunchedEffect(Unit) {
        sheetState.show()
    }
    
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 16.dp)),
        containerColor = whiteColor,
        dragHandle = null,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getHorizontalPadding())
        ) {
            CustomLabel(
                header = "Add New User",
                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                headerColor = onSurfaceColor,
                modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp))
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 16.dp)))
            
            // Role Selection
            CustomLabel(
                header = "Role",
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                headerColor = onSurfaceColor,
                modifier = Modifier.padding(bottom = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
            )
            
            FilteredAppTextField(
                value = if (selectedRole.isEmpty()) "" else selectedRole,
                onValueChange = { },
                placeholder = "Select Role",
                items = roleOptions,
                onItemSelected = onRoleSelected,
                modifier = Modifier.fillMaxWidth()
            )
            
            if (selectedRole.isNotEmpty()) {
                Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp)))
                CustomLabel(
                    header = "Email must end with: $expectedDomain",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = primaryColor.copy(alpha = 0.7f)
                )
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)))
            
            // Email Field
            CustomLabel(
                header = "Email",
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                headerColor = onSurfaceColor,
                modifier = Modifier.padding(bottom = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
            )
            
            AppIconTextField(
                value = email,
                onValueChange = onEmailChange,
                placeholder = "Enter email (e.g., name$expectedDomain)",
                icon = painterResource(R.drawable.ic_mail),
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)))
            
            // Password Field
            CustomLabel(
                header = "Password",
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                headerColor = onSurfaceColor,
                modifier = Modifier.padding(bottom = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
            )
            
            AppIconTextField(
                value = password,
                onValueChange = onPasswordChange,
                placeholder = "Enter password",
                icon = painterResource(R.drawable.ic_password),
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )
            
            // Error Message
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)))
                CustomLabel(
                    header = errorMessage,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = errorColor
                )
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(24.dp, 28.dp, 32.dp)))
            
            // Bottom Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppButton(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    buttonText = "Cancel",
                    containerColor = cardColor,
                    contentColor = onSurfaceColor,
                    onClick = {
                        coroutineScope.launch {
                            sheetState.hide()
                            onDismiss()
                        }
                    }
                )
                AppButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Add User",
                    onClick = {
                        // Validate inputs
                        if (email.isBlank() || password.isBlank() || selectedRole.isBlank()) {
                            onError("Please fill in all fields")
                            return@AppButton
                        }
                        
                        // Validate email domain matches role
                        if (!email.lowercase().endsWith(expectedDomain)) {
                            onError("Email must end with $expectedDomain for $selectedRole role")
                            return@AppButton
                        }
                        
                        // Add user
                        coroutineScope.launch {
                            val result = usersRepository.addUser(
                                email = email,
                                password = password,
                                role = selectedRole
                            )
                            
                            result.onSuccess {
                                coroutineScope.launch {
                                    sheetState.hide()
                                }
                                onUserAdded(true)
                            }.onFailure { exception ->
                                onError(exception.message ?: "Failed to add user")
                            }
                        }
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserManagementScreenPreview() {
    val navController = rememberNavController()
    UserManagementScreen(navController = navController)
}
