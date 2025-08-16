package com.example.labinventory.ui.screens

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.TabRowDefaults.Divider
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.labinventory.R
import com.example.labinventory.data.model.EquipmentCategory
import com.example.labinventory.data.model.UiState
import com.example.labinventory.data.model.categories
import com.example.labinventory.data.schema.Facilities
import com.example.labinventory.navigation.Screen
import com.example.labinventory.ui.components.AppCategoryIcon
import com.example.labinventory.ui.components.AppCircularIcon
import com.example.labinventory.ui.components.AppSearchBar
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomNavigationBar
import com.example.labinventory.ui.components.CustomTopBar
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.categoryColor
import com.example.labinventory.ui.theme.categoryIconColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.lightTextColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.FacilitiesViewModel
import com.example.labinventory.viewmodel.FilterSortViewModel
import com.example.labinventory.viewmodel.ItemsViewModel
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
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
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
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(horizontal = pxToDp(16)),
                        verticalArrangement = Arrangement.spacedBy(pxToDp(13)),
                        horizontalArrangement = Arrangement.spacedBy(pxToDp(13)),
                    ) {
                        items(items.data) { item ->
                            EquipmentCard(
                                image = item.image_url,
                                equipName = item.name,
                                available = if (item.is_available == true) "Available" else "Not Available",
                                onClick = { navController.navigate(Screen.ProductDescriptionScreen.route) },
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
                        modifier = Modifier.padding(16.dp)
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
    selectedIconColor: Color = highlightColor,
    iconColor: Color = categoryIconColor,
    selectedLabelColor: Color = categoryColor,
    labelColor: Color = darkTextColor.copy(0.4f),
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

        Spacer(modifier = Modifier.height(pxToDp(8)))

        CustomLabel(
            header = category.label,
            fontSize = 10.sp,
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
            start = pxToDp(28), end = pxToDp(28),
            top = pxToDp(12), bottom = pxToDp(8)
        ),
        horizontalArrangement = Arrangement.spacedBy(pxToDp(37)),
        modifier = Modifier.height(pxToDp(64))
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

                Spacer(modifier = Modifier.height(pxToDp(8)))

                if (category.id == selectedCategoryId) {
                    Box(
                        Modifier
                            .width(pxToDp(30))
                            .height(pxToDp(1))
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
    imageHeight: Dp = pxToDp(191),
    detailHeight: Dp = pxToDp(69),
    isSaved : Boolean = false,
    saveClick : () -> Unit = {},
    facilityName : String
) {
    val itemViewModel : ItemsViewModel = koinViewModel()

    Card(
        modifier = Modifier
            .padding(top = pxToDp(12), bottom = pxToDp(17), start = pxToDp(17), end = pxToDp(12)),
        onClick = onClick,
        shape = shape
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(cardColor)
                    .height(imageHeight)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = image,
                    contentDescription = "Equipment Image",
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(horizontal = pxToDp(12), vertical = pxToDp(12)),
                    contentScale = ContentScale.Crop
                )
                AppCategoryIcon(
                    painter = painterResource(R.drawable.ic_save),
                    iconDescription = "Save icon",
                    iconSize = pxToDp(18),
                    tint = if (isSaved) highlightColor else navLabelColor,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(pxToDp(8))
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
                    .padding(top = pxToDp(6)),
                horizontalAlignment = Alignment.Start
            ) {
                CustomLabel(
                    header = equipName,
                    headerColor = darkTextColor,
                    fontSize = pxToDp(12).value.sp,
                    modifier = Modifier.padding(top = pxToDp(2))
                )

                CustomLabel(
                    header =  available,
                    headerColor = highlightColor,
                    fontSize = pxToDp(12).value.sp,
                    modifier = Modifier.padding(top = pxToDp(3), bottom = pxToDp(3))
                )

                CustomLabel(
                    header = facilityName,
                    headerColor = lightTextColor,
                    fontSize = pxToDp(12).value.sp,
                    modifier = Modifier.padding(bottom = pxToDp(3))
                )

                Row {
                    AppCategoryIcon(
                        painter = painterResource(R.drawable.ic_location),
                        iconDescription = "location icon",
                        iconSize = pxToDp(12),
                        tint = lightTextColor
                    )
                    Spacer(modifier = Modifier.width(pxToDp(5)))
                    CustomLabel(
                        header = "IDC, Photo Studio",
                        headerColor = lightTextColor,
                        fontSize = pxToDp(12).value.sp,
                        modifier = Modifier.padding(bottom = pxToDp(0))
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


