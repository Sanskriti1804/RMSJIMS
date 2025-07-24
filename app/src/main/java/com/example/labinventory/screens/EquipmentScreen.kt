package com.example.labinventory.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.R
import com.example.labinventory.components.AppCategoryIcon
import com.example.labinventory.components.AppCategoryImage
import com.example.labinventory.components.AppCircularIcon
import com.example.labinventory.components.AppSearchBar
import com.example.labinventory.components.CustomLabel
import com.example.labinventory.equipments.data.EquipmentCategory
import com.example.labinventory.equipments.data.categories
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.lightTextColor


@Composable
fun EquipmentScreen(){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2)
    ) {
        item(
            span = {GridItemSpan(2)}
        ) {
            Row {
                AppSearchBar(
                    query = "",
                    onQueryChange = {},
                    onSearch = {},
                    placeholder = "Equipments, Tools, Supplies, etc..."
                )

                AppCircularIcon()
            }
        }

        item(
            span = {GridItemSpan(2)}
        ) {
            categories.forEach { category ->
                CategoryItem(category = category)
            }
        }

        item {
            EquipmentCard(
                onClick = {}
            )
        }
    }
}

@Composable
fun CategoryItem(category: EquipmentCategory) {
    Row (
        modifier = Modifier.padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(11.dp)
    ){
        Column(
            modifier = Modifier
                .padding()
        ) {
            AppCategoryIcon(
                painter = painterResource(id = category.categoryImage),
                iconDescription = category.label
            )

            CustomLabel(
                header = category.label,
                fontSize = 10.sp,
                modifier = Modifier.padding(top = 3.dp, bottom = 3.dp)
            )
        }
    }
}


@Composable
fun EquipmentCard(
    onClick: () -> Unit = {},
    padding : Dp = 4.dp,
//    shape: Shape = Shapes.CardShape,
//    elevation: CardElevation = CardDefaults.cardElevation(Dimensions.cardElevation),
    containerColor: Color = cardColor

){
    Card(
        modifier = Modifier
            .padding(padding),
        onClick = onClick,
//        elevation = elevation,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column {
            Box(
                modifier = Modifier
                    .padding(3.dp)
            ){
                AppCategoryImage(
                    painter = painterResource(R.drawable.temp),
                    modifier = Modifier.size(33.dp).align(Alignment.Center)
                )
                AppCategoryIcon(
                    painter = painterResource(R.drawable.ic_save),
                    iconDescription = "Save icon",
                    iconSize = 6.dp,
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
            Column(
            ) {
                CustomLabel(
                    header = "Canon EOS 1DX Mark III",
                    headerColor = darkTextColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 2.dp)
                )

                CustomLabel(
                    header = "Available" ,
                    headerColor = highlightColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 1.dp)
                )

                CustomLabel(
                    header = "Prof. Sumant Rao" ,
                    headerColor = lightTextColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 1.dp)
                )

                Row {
                    AppCategoryIcon(
                        painter = painterResource(R.drawable.ic_location),
                        iconDescription = "location icon",
                        iconSize = 3.dp
                    )
                    CustomLabel(
                        header = "IDC, Photo Studio" ,
                        headerColor = lightTextColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 1.dp)
                    )

                }

            }
        }

    }
}