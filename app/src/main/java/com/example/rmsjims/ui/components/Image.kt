package com.example.rmsjims.ui.components

import android.R.attr.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.rmsjims.R
import com.example.rmsjims.ui.theme.Dimensions

@Composable
fun AppLogoImage(
    profile : Painter = painterResource(R.drawable.jims_logo),
    userProfileDesc : String = "User Profile Photo",
    contentScale : ContentScale = ContentScale.Crop,
    size : Dp = 100.dp
){
    Image(
        painter = profile,
        contentDescription = userProfileDesc,
        modifier = Modifier.padding(Dimensions.componentPadding())
            .size(size),
        contentScale = contentScale,

    )
}

//@Composable
//fun ProfileImage(
//    profile : Painter = painterResource(R.drawable.jims_logo),
//    userProfileDesc : String = "User Profile Photo",
//    contentScale : ContentScale = ContentScale.Crop
//){
//    Image(
//        painter = profile,
//        contentDescription = userProfileDesc,
//        modifier = padding(Dimensions.componentPadding)
//            .clip(Shapes.ProfilePhoto)
//            .size(Dimensions.profileSize),
//        contentScale = contentScale,
//
//        )
//}

@Composable
fun AppCategoryImage(
    painter : Painter = painterResource(R.drawable.temp),
    reviewDesc : String = "category Image",
    contentScale: ContentScale = ContentScale.Crop,
    modifier: Modifier
){
    Image(
        painter = painter,
        contentDescription = reviewDesc,
        contentScale = contentScale,
        modifier = modifier
    )
}

//@Composable
//fun CustomDiscountImage(
//    painter : Painter ,
//    reviewDesc : String = "Discount Image Description",
//    contentScale: ContentScale = ContentScale.Crop
//){
//    Image(
//        painter = painter,
//        contentDescription = reviewDesc,
//        modifier = padding(Dimensions.imagePadding),
//        contentScale = contentScale
//    )
//}

