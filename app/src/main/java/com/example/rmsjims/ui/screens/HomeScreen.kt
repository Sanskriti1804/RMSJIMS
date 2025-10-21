package com.example.rmsjims.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppCategoryImage
import com.example.rmsjims.ui.components.AppCircularIcon
import com.example.rmsjims.ui.components.AppFAB
import com.example.rmsjims.ui.components.AppSearchBar
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.categoryColor
import com.example.rmsjims.ui.theme.titleColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.ItemCategoriesViewModel
import com.example.rmsjims.viewmodel.FilterSortViewModel
import com.example.rmsjims.viewmodel.SearchViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    categoryViewModel: ItemCategoriesViewModel = koinViewModel(),
    filterSortViewModel: FilterSortViewModel = koinViewModel(),
    sessionViewModel: UserSessionViewModel = koinViewModel(),
    searchViewModel: SearchViewModel = koinViewModel()
) {
    val isFilterSheetVisible by filterSortViewModel.isSheetVisible
    val isAiChatSheetVisible by searchViewModel.isChatSheetVisible

    val userRole = sessionViewModel.userRole

    val categories = categoryViewModel.categoriesState

    Scaffold(
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            if (userRole == UserRole.USER){
                AppFAB(
                    onClick = {searchViewModel.showChatSheet()}
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
            // Top Search Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = ResponsiveLayout.getHorizontalPadding(),
                        end = ResponsiveLayout.getHorizontalPadding(),
                        top = ResponsiveLayout.getVerticalPadding(),
                        bottom = ResponsiveLayout.getVerticalPadding()
                    )
            ) {
                AppSearchBar(
                    query = "",
                    onQueryChange = {},
                    modifier = Modifier
                        .height(ResponsiveLayout.getResponsiveSize(46.dp, 60.dp, 68.dp))
                        .weight(1f),
                  )

                Spacer(modifier = Modifier.width(ResponsiveLayout.getCardSpacing()))

                AppCircularIcon(
                    onClick = { filterSortViewModel.showSheet() }
                )
            }
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))

            CustomLabel(
                "Explore by Category",
                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                modifier = Modifier
                    .padding(
                        start = ResponsiveLayout.getHorizontalPadding()
                    ),
                headerColor = titleColor
            )

            if (userRole == UserRole.LAB_INCHARGE) {
                AppButton(
                    buttonText = "Manage Lab",
                    onClick = { },
                    modifier = Modifier
                        .padding(
                            horizontal = ResponsiveLayout.getHorizontalPadding(), 
                            vertical = ResponsiveLayout.getVerticalPadding()
                        )
                        .fillMaxWidth()
                )
            }

            if (isFilterSheetVisible) {
                FilterSortBottomSheet(viewModel = filterSortViewModel)
            }

            if (isAiChatSheetVisible){
                ChatBottomSheet(viewModel = searchViewModel)
            }

            when (categories) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is UiState.Success -> {
                    LazyVerticalGrid(
                        columns = ResponsiveLayout.getGridColumns(),
                        contentPadding = ResponsiveLayout.getContentPadding(),
                        verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                        horizontalArrangement = ResponsiveLayout.getGridArrangement(),
                    ) {
                        items(categories.data) { item ->
                            AppCategoryCard(
                                title = item.name,
                                onClick = { navController.navigate(Screen.EquipmentScreen.createRoute(item.name) )}
                            )
                        }
                    }
                }

                is UiState.Error -> {
                    Text(
                        text = "Error loading categories",
                        color = Color.Red,
                        modifier = Modifier.padding(ResponsiveLayout.getVerticalPadding())
                    )
                }
            }
        }
    }
}

@Composable
fun AppCategoryCard(
    onClick: () -> Unit = {},
    title : String,
    containerColor: Color = onSurfaceVariant,
    shape: Shape = RectangleShape
){
    Card(
        modifier = Modifier
            .height(ResponsiveLayout.getResponsiveSize(110.dp, 160.dp, 200.dp)),
        onClick = onClick,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 24.dp, 32.dp))
        ){

            CustomLabel(
                header = title,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(0.8f),
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                headerColor = categoryColor
            )

            AppCategoryImage(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController : NavHostController = rememberNavController()
    HomeScreen(navController =  navController)
}

