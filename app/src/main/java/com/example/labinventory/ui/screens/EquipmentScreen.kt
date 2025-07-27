package com.example.labinventory.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.R
import com.example.labinventory.data.model.EquipmentCategory
import com.example.labinventory.data.model.categories
import com.example.labinventory.ui.components.AppCategoryIcon
import com.example.labinventory.ui.components.AppCategoryImage
import com.example.labinventory.ui.components.AppCircularIcon
import com.example.labinventory.ui.components.AppSearchBar
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomNavigationBar
import com.example.labinventory.ui.components.CustomTopBar
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.lightTextColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquipmentScreen(){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    Scaffold (
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CustomTopBar(
                title = "Film",
           )
        },
        containerColor = whiteColor,
        bottomBar = {
            CustomNavigationBar()
        },
    ){
        LazyVerticalGrid(
            contentPadding = it,
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize()
        ) {
            item(
                span = {GridItemSpan(2)}
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = pxToDp(17), end = pxToDp(16), top = pxToDp(16), bottom = pxToDp(12))
                ){
                    AppSearchBar(
                        query = "",
                        onQueryChange = {},
                        modifier = Modifier
                            .height(pxToDp(46))
                            .weight(1f),
                        placeholder = "Equipments, Tools, Supplies, etc..."
                    )

                    Spacer(modifier = Modifier.width(pxToDp(8)))

                    AppCircularIcon()
                }
            }

            item(
                span = {GridItemSpan(2)}
            ) {
                CategoryRow(categories = categories)
            }

            item {
                EquipmentCard(
                    onClick = {}
                )
            }
        }
    }

}

@Composable
fun CategoryItem(category: EquipmentCategory) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.padding(horizontal = pxToDp(20)) // 41px / 2 spacing per side
    ) {
        AppCategoryIcon(
            painter = painterResource(id = category.categoryImage),
            iconDescription = category.label
        )

        Spacer(modifier = Modifier.height(pxToDp(8)))

        CustomLabel(
            header = category.label,
            fontSize = 10.sp,
            modifier = Modifier,
            headerColor = darkTextColor.copy(0.4f)
        )
    }
}

@Composable
fun CategoryRow(categories: List<EquipmentCategory>) {
    LazyRow(
        contentPadding = PaddingValues(start = pxToDp(28), end = pxToDp(28), top = pxToDp(12), bottom = pxToDp(8)),
        horizontalArrangement = Arrangement.spacedBy(pxToDp(37)),
        modifier = Modifier.height(pxToDp(64))
    ) {
        items(categories) { category ->
            CategoryItem(category = category)
        }
    }
}
@Composable
fun EquipmentCard(
    onClick: () -> Unit = {},
    shape: Shape = RectangleShape,
    cardHeight: Dp = pxToDp(260),
    imageHeight: Dp = pxToDp(191),
    detailHeight: Dp = pxToDp(69),
) {
    Card(
        modifier = Modifier
            .padding(top = pxToDp(12), bottom = pxToDp(17), start = pxToDp(17), end = pxToDp(12))
            .height(cardHeight),
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
                AppCategoryImage(
                    painter = painterResource(R.drawable.temp),
                    modifier = Modifier
                        .size(pxToDp(33))
                        .align(Alignment.Center)
                        .padding(top = pxToDp(13), bottom = pxToDp(13)),
                    contentScale = ContentScale.FillBounds
                )
                AppCategoryIcon(
                    painter = painterResource(R.drawable.ic_save),
                    iconDescription = "Save icon",
                    iconSize = pxToDp(18),
                    tint = navLabelColor,
                    modifier = Modifier.align(Alignment.TopEnd)
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
                    header = "Canon EOS 1DX Mark III",
                    headerColor = darkTextColor,
                    fontSize = pxToDp(12).value.sp,
                    modifier = Modifier.padding(top = pxToDp(2))
                )

                CustomLabel(
                    header = "Available",
                    headerColor = highlightColor,
                    fontSize = pxToDp(12).value.sp,
                    modifier = Modifier.padding(top = pxToDp(3), bottom = pxToDp(3))
                )

                CustomLabel(
                    header = "Prof. Sumant Rao",
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
    EquipmentScreen()
//    EquipmentCard()
//    CategoryRow(categories = categories)
}


