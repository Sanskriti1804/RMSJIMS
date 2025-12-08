package com.example.rmsjims.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.rmsjims.R
import com.example.rmsjims.data.model.EquipmentCategory
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.model.categories
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppCategoryIcon
import com.example.rmsjims.ui.components.AppCircularIcon
import com.example.rmsjims.ui.components.AppSearchBar
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.categoryColor
import com.example.rmsjims.ui.theme.categoryIconColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.lightTextColor
import com.example.rmsjims.ui.theme.navLabelColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.FacilitiesViewModel
import com.example.rmsjims.viewmodel.FilterSortViewModel
import com.example.rmsjims.viewmodel.ItemsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentScreen(
    navController: NavHostController,
    filterSortViewModel: FilterSortViewModel = koinViewModel(),
    itemViewModel: ItemsViewModel = koinViewModel(),
    facilitiesViewModel: FacilitiesViewModel = koinViewModel(),
    categoryName : String
){
    var isFilterSheetVisible by filterSortViewModel.isSheetVisible
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    var isSaved by remember { mutableStateOf(false) }

    val items = itemViewModel.itemsState
    val facilitiesState = facilitiesViewModel.facilitiesState

    Scaffold (
        topBar = {
            CustomTopBar(
                title = categoryName,
                onNavigationClick = {
                    navController.popBackStack()
                }
           )
        },
        containerColor = whiteColor,
        bottomBar = {
            CustomNavigationBar(
                navController = navController
            )
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
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
                        .weight(1f)
                )

                Spacer(modifier = Modifier.width(ResponsiveLayout.getCardSpacing()))

                AppCircularIcon(
                    onClick = { filterSortViewModel.showSheet() }
                )
            }

            CategoryRow(categories = categories)

            when (items) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is UiState.Success -> {

                    val facilities = (facilitiesState as? UiState.Success)?.data ?: emptyList()

                    LazyVerticalGrid(
                        columns = ResponsiveLayout.getGridColumns(),
                        contentPadding = ResponsiveLayout.getContentPadding(),
                        verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                        horizontalArrangement = ResponsiveLayout.getGridArrangement(),
                    ) {
                        items(items.data) { item ->
                            EquipmentCard(
                                image = item.image_url,
                                equipName = item.name,
                                available = if (item.is_available == true) "Available" else "Not Available",
                                onClick = { navController.navigate(Screen.ProductDescriptionScreen.createRoute(item.id)) },
                                saveClick = {
                                    isSaved = !isSaved
                                },
                                facilityName = itemViewModel.getFacilityNameForEquipment(item, facilities)
                            )
                        }
                    }
                }

                is UiState.Error -> {
                    val errorMessage = items.exception.localizedMessage ?: "Something went wrong"
                    Log.e("EquipmentScreen", "Error loading items", items.exception)

                    Text(
                        text = "Error loading items: $errorMessage",
                        color = Color.Red,
                        modifier = Modifier.padding(ResponsiveLayout.getVerticalPadding())
                    )
                }
            }
        }

        if (isFilterSheetVisible){
            FilterSortBottomSheet(viewModel = filterSortViewModel)
        }
    }
}

@Composable
fun CategoryItem(
    category: EquipmentCategory,
    isSelected : Boolean = false,
    selectedIconColor: Color = primaryColor,
    iconColor: Color = categoryIconColor,
    selectedLabelColor: Color = categoryColor,
    labelColor: Color = onSurfaceColor.copy(0.4f),
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable{
            onClick()
        }
    ) {
        AppCategoryIcon(
            painter = painterResource(id = category.categoryImage),
            iconDescription = category.label,
            tint = if (isSelected) selectedIconColor else iconColor
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp)))

        CustomLabel(
            header = category.label,
            fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
            modifier = Modifier,
            headerColor = if (isSelected) selectedLabelColor else labelColor
        )
    }
}

@Composable
fun CategoryRow(categories: List<EquipmentCategory>) {
    var selectedCategoryId by remember { mutableIntStateOf(categories.first().id) }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = listState,
        contentPadding = PaddingValues(
            start = ResponsiveLayout.getHorizontalPadding(), 
            end = ResponsiveLayout.getHorizontalPadding(),
            top = ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp), 
            bottom = ResponsiveLayout.getResponsiveSize(8.dp, 12.dp, 16.dp)
        ),
        horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(37.dp, 42.dp, 48.dp)),
//        modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(64.dp, 72.dp, 80.dp))
    ) {
        itemsIndexed(categories) { index, category ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable {
                    selectedCategoryId = category.id
                    coroutineScope.launch {
                        listState.animateScrollToItem(index)
                    }
                }
            ) {
                CategoryItem(
                    category = category,
                    isSelected = category.id == selectedCategoryId,
                    onClick = {  }
                )

                Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp)))

                if (category.id == selectedCategoryId) {
                    Box(
                        Modifier
                            .width(ResponsiveLayout.getResponsiveSize(30.dp, 36.dp, 42.dp))
                            .height(ResponsiveLayout.getResponsiveSize(1.dp, 1.5.dp, 2.dp))
                            .background(categoryColor)
                    )
                }
            }
        }
    }
}


@Composable
fun EquipmentCard(
    image : Any,
    available : String,
    equipName : String,
    onClick: () -> Unit = {},
    shape: Shape = RectangleShape,
    imageHeight: Dp = ResponsiveLayout.getResponsiveSize(125.dp, 140.dp, 160.dp),
    detailHeight: Dp = ResponsiveLayout.getResponsiveSize(75.dp, 85.dp, 95.dp),
    isSaved : Boolean = false,
    saveClick : () -> Unit = {},
    facilityName : String
) {
    val itemViewModel : ItemsViewModel = koinViewModel()

    Card(
        modifier = Modifier,
        onClick = onClick,
        shape = shape
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(onSurfaceVariant)
                    .height(imageHeight)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "Equipment Image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(
                            horizontal = ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp), 
                            vertical = ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)
                        ),
                    contentScale = ContentScale.Crop
                )
                AppCategoryIcon(
                    painter = painterResource(R.drawable.ic_save),
                    iconDescription = "Save icon",
                    iconSize = ResponsiveLayout.getResponsiveSize(18.dp, 20.dp, 24.dp),
                    tint = if (isSaved) primaryColor else navLabelColor,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp))
                        .clickable{
                            saveClick()
                        }
                )
            }

            Column(
                modifier = Modifier
                    .height(detailHeight)
                    .fillMaxWidth()
                    .background(whiteColor)
                    .padding(top = ResponsiveLayout.getResponsiveSize(6.dp, 8.dp, 10.dp)),
                horizontalAlignment = Alignment.Start
            ) {
                CustomLabel(
                    header = equipName,
                    headerColor = onSurfaceColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    modifier = Modifier.padding(top = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp))
                )

                CustomLabel(
                    header =  available,
                    headerColor = primaryColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    modifier = Modifier.padding(
                        top = ResponsiveLayout.getResponsiveSize(3.dp, 4.dp, 5.dp), 
                        bottom = ResponsiveLayout.getResponsiveSize(3.dp, 4.dp, 5.dp)
                    )
                )

                CustomLabel(
                    header = facilityName,
                    headerColor = lightTextColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    modifier = Modifier.padding(bottom = ResponsiveLayout.getResponsiveSize(3.dp, 4.dp, 5.dp))
                )

                Row {
                    AppCategoryIcon(
                        painter = painterResource(R.drawable.ic_location),
                        iconDescription = "location icon",
                        iconSize = ResponsiveLayout.getResponsiveSize(12.dp, 14.dp, 16.dp),
                        tint = lightTextColor
                    )
                    Spacer(modifier = Modifier.width(ResponsiveLayout.getResponsiveSize(5.dp, 6.dp, 8.dp)))
                    CustomLabel(
                        header = "IDC, Photo Studio",
                        headerColor = lightTextColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        modifier = Modifier.padding(bottom = 0.dp)
                    )
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun EquipmentScreenPreview() {
    val navController : NavHostController = rememberNavController()
//    EquipmentScreen(navController)
//    EquipmentCard()
//    CategoryRow(categories = categories)
}


