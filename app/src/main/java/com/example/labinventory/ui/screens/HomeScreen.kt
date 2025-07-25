package com.example.labinventory.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.labinventory.ui.components.AppCategoryCard
import com.example.labinventory.ui.components.AppCircularIcon
import com.example.labinventory.ui.components.AppSearchBar

import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.theme.Dimensions

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(Dimensions.appPadding)) {
        Row {
            AppSearchBar(
                query = "",
                onQueryChange = {},
                onSearch = {},
                placeholder = "Equipments, Tools, Supplies, etc..."
            )

            AppCircularIcon()
        }


        CustomLabel(
            "Explore by Category",
            modifier = Modifier
                .padding(top = 12.dp, bottom = 4.dp)
            )

        AppCategoryCard(
            title = "Film"
        )

//
//        CategoryGrid(items = categoryItems)
    }
}
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2)
//    ) {
//        item(
//            span = { GridItemSpan(2) }) {
//            Column(
//                modifier = Modifier.padding(8.dp)
//            ) {
//                //            CustomSearch()
////                CustomButton()
//            }
//        }
//
//        item(
//            span = { GridItemSpan(2) }) {
//            CustomLabel(header = "Explore by Category")
//        }
//        item {
//            CustomCard() {
//                Box(modifier = Modifier.fillMaxSize()) {
//                    Text(
//                        text = "Film",
//                        modifier = Modifier
//                            .align(Alignment.TopStart)
//                            .padding(8.dp)
//                    )
//                    Image(
//                        painter = painterResource(id = R.drawable.ic_launcher_background), // Replace with your image resource
//                        contentDescription = "Film Image",
//                        modifier = Modifier
//                            .align(Alignment.BottomEnd)
//                            .padding(8.dp)
//                    )
//                }
//            }
//        }

//    }
//}

//@Composable
//fun CategoryGrid(
//    items: List<Pair<String, Painter>>
//) {
//    LazyVerticalGrid(
//        columns = GridCells.Fixed(2),
//        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp),
//        horizontalArrangement = Arrangement.spacedBy(16.dp),
//        modifier = Modifier.fillMaxSize()
//    ) {
//        items(items) { (title, painter) ->
//            CustomCategoryCard(
//                title = "helloo",
////                imagePainter = ,
//                onClick = {},
//            )
//        }
//    }
//}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}

