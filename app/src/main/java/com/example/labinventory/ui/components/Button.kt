package com.example.labinventory.ui.components

import android.R.attr.padding
import android.graphics.drawable.Icon
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.Dimensions
import com.example.labinventory.ui.theme.Shapes
import com.example.labinventory.ui.theme.app_llComponent
import com.example.labinventory.ui.theme.app_ltext
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.headerColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.navBackColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay
import org.koin.core.time.TimeInMillis
import kotlin.toString


@Composable
fun AppButton(
    onClick : () -> Unit = {},
    containerColor: Color = highlightColor,
    contentColor : Color = whiteColor,
    buttonText: String,
    shape: Shape = RectangleShape,
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(pxToDp(52)),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        content = {
                Text(
                    text = buttonText,
                    fontSize = 16.sp,
                    color = contentColor
                )
        }
    )
}

@Composable
fun EditButton(
    onClick : () -> Unit = {},
    containerColor: Color = darkTextColor.copy(0.08f),
    contentColor : Color = highlightColor,
    buttonText: String = "Edit",
    icon: Painter = painterResource(R.drawable.ic_edit),
    iconSize: Dp = pxToDp(12),
    shape: Shape = RoundedCornerShape(pxToDp(20)),
    modifier: Modifier = Modifier
){
  Button(
      onClick = onClick,
      modifier = Modifier.defaultMinSize(minHeight = 0.dp),
      colors = ButtonDefaults.buttonColors(
          containerColor = containerColor,
          contentColor = contentColor
      ),
      contentPadding = PaddingValues(0.dp),
      shape = shape,
  ) {
      Row(
          modifier = Modifier.padding(horizontal = pxToDp(10), vertical = pxToDp(0)),
          horizontalArrangement = Arrangement.spacedBy(pxToDp(6)),
      ) {
          Icon(
              painter = icon,
              contentDescription = "Edit Icon",
              tint = contentColor,
              modifier = Modifier.size(iconSize)
          )
          CustomLabel(
              header = buttonText,
              headerColor = contentColor,
              fontSize = 10.sp
          )
      }
  }
}

@Preview(showBackground = true)
@Composable
fun AppButtonPreview() {
//    AppButton(
//        buttonText = "Sample Button"
//    )
    EditButton()
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppFAB(
    showIntro : Boolean = true,
    delay: Int = 5000,
    onClick : () -> Unit ={},
    contentColor: Color = whiteColor,
    containerColor: Color = navBackColor,
    capsuleShape: Shape = RoundedCornerShape(pxToDp(30)),
    iconShape: Shape = CircleShape,
){
    var showLabel by rememberSaveable { mutableStateOf(showIntro) }

    val ai_chatIcon = painterResource(R.drawable.ic_aichat)


    LaunchedEffect(showLabel) {
        delay(delay.toLong())
        showLabel = false
    }

    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier
            .wrapContentWidth(),
        containerColor = containerColor,
        contentColor = contentColor,
        shape = if (showLabel) capsuleShape else iconShape,

    ) {
        AnimatedContent(
            targetState = showLabel,
            transitionSpec = {
                fadeIn(tween(3000)) + scaleIn() with fadeOut(tween(3000)) + scaleOut()
            },
            label = "FAB animation"
        ) {targetState ->
            if (targetState) {
                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(horizontal = pxToDp(18), vertical = pxToDp(16)),
                    horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = ai_chatIcon,
                        contentDescription = "FAB Icon",
                        tint = Color.Unspecified
                    )
                    CustomLabel(
                        header = "Share your project to get equipment suggestions",
                        headerColor = contentColor,
                        fontSize = 14.sp
                    )
                }
            } else {
                Icon(
                    painter = ai_chatIcon,
                    contentDescription = "FAB Icon",
                    tint = Color.Unspecified
                )
            }
        }
    }
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