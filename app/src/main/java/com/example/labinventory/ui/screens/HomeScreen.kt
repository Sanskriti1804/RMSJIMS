package com.example.labinventory.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
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
import com.example.labinventory.data.model.UiState
import com.example.labinventory.data.model.UserRole
import com.example.labinventory.navigation.Screen
import com.example.labinventory.ui.components.AppButton
import com.example.labinventory.ui.components.AppCategoryImage
import com.example.labinventory.ui.components.AppCircularIcon
import com.example.labinventory.ui.components.AppFAB
import com.example.labinventory.ui.components.AppSearchBar
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomNavigationBar
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.categoryColor
import com.example.labinventory.ui.theme.titleColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.ItemCategoriesViewModel
import com.example.labinventory.viewmodel.FilterSortViewModel
import com.example.labinventory.viewmodel.SearchViewModel
import com.example.labinventory.viewmodel.UserSessionViewModel
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
                        start = pxToDp(14),
                        end = pxToDp(14),
                        top = pxToDp(19),
                        bottom = pxToDp(37)
                    )
            ) {
                AppSearchBar(
                    query = "",
                    onQueryChange = {},
                    modifier = Modifier
                        .height(pxToDp(46))
                        .weight(1f),
                    placeholder = "Equipments, Tools, Supplies, etc..."
                )

                Spacer(modifier = Modifier.width(pxToDp(8)))

                AppCircularIcon(
                    onClick = { filterSortViewModel.showSheet() }
                )
            }

            CustomLabel(
                "Explore by Category",
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(start = pxToDp(16), bottom = pxToDp(13)),
                headerColor = titleColor
            )

            if (userRole == UserRole.LAB_INCHARGE) {
                AppButton(
                    buttonText = "Manage Lab",
                    onClick = { },
                    modifier = Modifier
                        .padding(horizontal = pxToDp(16), vertical = pxToDp(16))
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
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = pxToDp(16)),
                        verticalArrangement = Arrangement.spacedBy(pxToDp(13)),
                        horizontalArrangement = Arrangement.spacedBy(pxToDp(13)),
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
                        modifier = Modifier.padding(16.dp)
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
    containerColor: Color = cardColor,
    shape: Shape = RectangleShape
){
    Card(
        modifier = Modifier
            .height(pxToDp(110)),
        onClick = onClick,
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pxToDp(16))
        ){

            CustomLabel(
                header = title,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .fillMaxWidth(0.8f),
                fontSize = 16.sp,
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

