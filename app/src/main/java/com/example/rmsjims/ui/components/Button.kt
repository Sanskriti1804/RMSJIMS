package com.example.rmsjims.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.navBackColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import kotlinx.coroutines.delay


@Composable
fun AppButton(
    onClick : () -> Unit = {},
    containerColor: Color = primaryColor,
    contentColor : Color = whiteColor,
    buttonText: String,
    shape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(4.dp, 6.dp, 8.dp)),
    modifier: Modifier = Modifier
){
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(ResponsiveLayout.getResponsiveSize(52.dp, 60.dp, 68.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = shape,
        content = {
                Text(
                    text = buttonText,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                    color = contentColor
                )
        }
    )
}

@Composable
fun EditButton(
    onClick : () -> Unit = {},
    containerColor: Color = onSurfaceColor.copy(0.08f),
    contentColor : Color = primaryColor,
    buttonText: String = "Edit",
    icon: Painter = painterResource(R.drawable.ic_edit),
    iconSize: Dp = ResponsiveLayout.getResponsiveSize(12.dp, 14.dp, 16.dp),
    shape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(20.dp, 24.dp, 28.dp)),
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
          modifier = Modifier.padding(
              horizontal = ResponsiveLayout.getResponsiveSize(10.dp, 12.dp, 16.dp), 
              vertical = 0.dp
          ),
          horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(6.dp, 8.dp, 10.dp)),
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
              fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp)
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
    modifier: Modifier,
    delay: Int = 5000,
    onClick : () -> Unit ={},
    contentColor: Color = whiteColor,
    containerColor: Color = navBackColor,
    capsuleShape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(30.dp, 36.dp, 42.dp)),
    iconShape: Shape = CircleShape,
    iconSize: Dp = ResponsiveLayout.getResponsiveSize(30.dp, 36.dp, 42.dp),
    icon : Painter = painterResource(R.drawable.ic_aichat)
){
    var showLabel by rememberSaveable { mutableStateOf(showIntro) }

    LaunchedEffect(showLabel) {
        delay(delay.toLong())
        showLabel = false
    }

    FloatingActionButton(
        onClick = onClick,
        modifier = modifier
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
                        .padding(
                             horizontal = ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp),
                             vertical = ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)
                        ),
                    horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = icon,
                        contentDescription = "AI Chat Icon",
                        modifier = Modifier.size(iconSize),
                        tint = contentColor
                    )
                    Text(
                        text = "AI Chat",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                        color = contentColor
                    )
                }
            } else {
                Icon(
                    painter = icon,
                    contentDescription = "AI Chat Icon",
                    modifier = Modifier.size(iconSize),
                    tint = contentColor
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