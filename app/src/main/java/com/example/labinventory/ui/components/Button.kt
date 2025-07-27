package com.example.labinventory.ui.components

import android.R.attr.padding
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.ui.theme.Dimensions
import com.example.labinventory.ui.theme.Shapes
import com.example.labinventory.ui.theme.app_llComponent
import com.example.labinventory.ui.theme.app_ltext
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.whiteColor
import kotlin.toString


@Composable
fun AppButton(
    onClick : () -> Unit = {},
    containerColor: Color = highlightColor,
    contentColor : Color = whiteColor,
    buttonText: String,
    modifier: Modifier = Modifier.padding(Dimensions.imagePadding)
){
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        content = {
            Text(
                text = buttonText,
                modifier = Modifier
                    .padding(5.dp),
//                    .align(Alignment.Center as Alignment.Vertical),
                fontSize = 14.sp,
                color = whiteColor
            )
        }
    )
}
//
//@Composable
//fun QuantityButton(
//    onClick: () -> Unit,
//    shape: Shape = Shapes.ButtonShape,
//    elevation: ButtonElevation = ButtonDefaults.buttonElevation(Dimensions.buttonElevation),
//    containerColor: Color = ,
//    contentColor: Color = app_llComponent,
//    borderColor : Color = app_llComponent,
//    borderThickness : Dp = Dimensions.buttonBorder,
//    upIcon : Painter = painterResource(R.drawable.ic_remove),
//    downIcon : ImageVector = Icons.Default.Add,
//    quantity : String,
//    padding : Dp = Dimensions.buttonPadding
//    ){
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .padding(padding)
//            .border(width = borderThickness, color = borderColor),
//        shape = shape,
//        elevation = elevation,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = containerColor,
//            contentColor = contentColor
//        )
//    ) {
//        Row(
//            modifier = Modifier.wrapContentWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            CustomIcon(
//                painter = painterResource(R.drawable.ic_app_arrow)
//            )
//            Text(
//                text = quantity
//            )
//            CustomIcon(
//                painter = painterResource(R.drawable.ic_app_arrow),
//            )
//        }
//    }
//}
//
//@Composable
//fun SizeButton(
//    onClick: () -> Unit,
//    shape: Shape = Shapes.ButtonShape,
//    elevation: ButtonElevation = ButtonDefaults.buttonElevation(Dimensions.buttonElevation),
//    containerColor: Color = Color.White,
//    contentColor: Color = Color.Black,
//    borderColor : Color = Color.Black,
//    borderThickness : Dp = Dimensions.buttonBorder,
//    upIcon : ImageVector = Icons.Default.KeyboardArrowUp,
//    downIcon : ImageVector = Icons.Default.KeyboardArrowDown,
//    quantity : String,
//    padding : Dp = Dimensions.buttonPadding
//
//){
//    Button(
//        onClick = onClick,
//        modifier = Modifier
//            .padding(padding)
//            .border(width = borderThickness, color = borderColor),
//        shape = shape,
//        elevation = elevation,
//        colors = ButtonDefaults.buttonColors(
//            containerColor = containerColor,
//            contentColor = contentColor
//        )
//    ) {
//        Column(
//            modifier = Modifier.wrapContentWidth().wrapContentHeight(),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = quantity
//            )
//
//            Row {
//                CustomIcon(
//                    painter = painterResource(R.drawable.ic_app_arrow)
//                )
//
//                CustomIcon(
//                    painter = painterResource(R.drawable.ic_app_arrow)
//                )
//            }
//        }
//    }
//}