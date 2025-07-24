package com.example.labinventory.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.labinventory.R

//@Composable
//fun CustomProfilePic(
//    profile : Painter = painterResource(R.drawable.profile),
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
//    )
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

