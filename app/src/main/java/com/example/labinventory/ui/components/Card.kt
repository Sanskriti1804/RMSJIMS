package com.example.labinventory.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.labinventory.ui.theme.cardColor


@Composable
fun AppCategoryCard(
    onClick: () -> Unit = {},
    title : String,
    padding : Dp = 4.dp,
//    shape: Shape = Shapes.CardShape,
//    elevation: CardElevation = CardDefaults.cardElevation(Dimensions.cardElevation),
    containerColor: Color = cardColor
){
    Card(
        modifier = Modifier
            .padding(padding)
            .aspectRatio(192f/109f),
        onClick = onClick,
//        elevation = elevation,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ){

            CustomLabel(
                header = "Film",
                modifier = Modifier
                    .align(Alignment.TopStart)
            )

            AppCategoryImage(
                modifier = Modifier.align(Alignment.BottomEnd),
            )
        }
    }
}

