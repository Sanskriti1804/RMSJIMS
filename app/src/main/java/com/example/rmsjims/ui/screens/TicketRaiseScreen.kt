package com.example.rmsjims.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppDropDownTextField
import com.example.rmsjims.ui.components.AppTextField
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.FilteredAppTextField
//import com.example.rmsjims.ui.screens.assisstant.AddImageCard
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.BranchViewModel
import com.example.rmsjims.viewmodel.DepartmentViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
@SuppressLint("RememberReturnType")
fun RaiseTicketScreen(
    navController: NavHostController,
    ){
    val branchViewModel : BranchViewModel = koinViewModel()
    val branchList = branchViewModel.branchName
    var value by remember { mutableStateOf("") }
    var selectedBranch by remember { mutableStateOf("") }

    val departmentViewModel : DepartmentViewModel = koinViewModel()
    val query = departmentViewModel.query
    val filteredDepartmentList = departmentViewModel.filteredDepartments

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Report an Issue",
                onNavigationClick = {
                    navController.popBackStack()
                }
            )
        },
        containerColor = whiteColor,
        bottomBar = {
            AppButton(
                buttonText = "SUBMIT",
                onClick = {
                    navController.navigate(Screen.BookingsScreen.route)
                },
                modifier = Modifier.padding(ResponsiveLayout.getHorizontalPadding())
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(ResponsiveLayout.getHorizontalPadding())
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(13.dp, 16.dp, 20.dp))
        ){
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)))
            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Resource Name"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Project Description"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AppDropDownTextField(
                    modifier = Modifier.weight(1f),
                    textColor = primaryColor,
                    containerColor = primaryColor.copy(0.2f),
                    value = value,
                    onValueChange = { value = it},
                    placeholder = "Priority",
                    items = listOf("High", "Medium", "Low")
                )

                when (branchViewModel.branchState) {
                    is UiState.Loading -> AppDropDownTextField(
                        modifier = Modifier.weight(1f),
                        value = "",
                        onValueChange = {},
                        placeholder = "Loading...",
                        items = emptyList()
                    )
                    is UiState.Error -> AppDropDownTextField(
                        modifier = Modifier.weight(1f),
                        value = "",
                        onValueChange = {},
                        placeholder = "Error loading",
                        items = emptyList()
                    )
                    is UiState.Success -> AppDropDownTextField(
                        modifier = Modifier.weight(1f),
                        value = selectedBranch,
                        onValueChange = { selectedBranch = it },
                        placeholder = "Category",
                        items = branchList
                    )
                }

            }

//            AddImageCard()

            Divider(thickness = 1.dp, color = onSurfaceColor.copy(0.2f))

            when (departmentViewModel.departmentState) {
                is UiState.Loading -> FilteredAppTextField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    onValueChange = {},
                    placeholder = "Loading..."
                )
                is UiState.Error -> FilteredAppTextField(
                    modifier = Modifier.weight(1f),
                    value = "",
                    onValueChange = {},
                    placeholder = "Error loading"
                )
                is UiState.Success -> FilteredAppTextField(
                    value = query,
                    onValueChange = { departmentViewModel.onQueryChange(it)},
                    placeholder = "Location",
                    items = filteredDepartmentList,
                    onItemSelected = {
                        departmentViewModel.onDepartmentSelected(it)
                    }
                )
            }

            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Expected Resolution Date"
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Provide the issue details and select the appropriate category and priority from the dropdowns",
                color = onSurfaceColor.copy(0.9f),
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                letterSpacing = 0.3.sp,
                lineHeight = 20.sp,
                softWrap = true,
                fontFamily =  FontFamily(Font(R.font.font_alliance_regular_two)),
            )

        }
    }
}
