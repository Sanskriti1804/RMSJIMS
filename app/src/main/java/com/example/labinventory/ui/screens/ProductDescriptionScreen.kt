package com.example.labinventory.ui.screens

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.labinventory.R
import com.example.labinventory.ui.components.AppButton
import com.example.labinventory.ui.components.AppCategoryIcon
import com.example.labinventory.ui.components.AppCircularIcon
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomTopBar
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.circularBoxColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay



@Composable
fun ProdDescScreen(
) {
    val productImage = listOf(
        R.drawable.temp,
        R.drawable.temp,
        R.drawable.temp
    )

    val pagerState = rememberPagerState(pageCount = { productImage.size })
    val pagerInteractionSource = remember { MutableInteractionSource() }
    val pagerIsPressed by pagerInteractionSource.collectIsPressedAsState()
    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()
    val autoAdvance = !pagerIsDragged && !pagerIsPressed

    LaunchedEffect(autoAdvance) {
        if (autoAdvance) {
            while (true) {
                delay(2000)
                val nextPage = (pagerState.currentPage + 1) % productImage.size
                pagerState.animateScrollToPage(page = nextPage)
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(title = "Camera")
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Spacer(modifier = Modifier.height(pxToDp(40)))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(pxToDp(16)),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            ProductCarousel(
                images = productImage,
                pageInteractionSource = pagerInteractionSource,
                pagerState = pagerState
            )

            ProductDescriptionCard(modifier = Modifier)

            InChargeCard()
            AdditionalInfoCard()
            UseCard()

            AppButton(
                onClick = {},
                buttonText = "BOOK NOW"
            )
        }
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductCarousel(
    images : List<Int>,
    imageDescription : String = "Equipment images",
    contentScale: ContentScale = ContentScale.Crop,
    pagerState: PagerState,
    pageInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor : Color = cardColor,
    inactiveColor : Color = Color.DarkGray,
    activeColor : Color = highlightColor,
    indicatorShape: Shape = CircleShape,
    indicatorSize : Dp = pxToDp(6)
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(pxToDp(200)),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(pxToDp(16))
        ) {
            HorizontalPager(
                state = pagerState, // Use the passed pagerState
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .clickable(
                        // interactionSource allows observing and controlling the visual state of the pager (e.g., pressed, hovered).
                        interactionSource = pageInteractionSource,
                        // indication provides visual feedback for interactions (e.g., ripples on click). LocalIndication.current uses the default indication provided by the theme.
                        indication = LocalIndication.current
                    ){}
            ) {
                    page ->
                AsyncImage(
                    model = images[page],
                    contentDescription = imageDescription,
                    contentScale = contentScale,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Spacer(modifier = Modifier.height(pxToDp(16)))
            HorizontalPagerIndicator(
                pagerState = pagerState, // Use the passed pagerState
                pageCount = images.size,
                inactiveColor = inactiveColor,
                activeColor = activeColor,
                indicatorShape = indicatorShape,
                modifier = Modifier
                    .size(indicatorSize)
            )
            Spacer(modifier = Modifier.height(pxToDp(3)))
        }

    }
}

@Composable
fun ProductDescriptionCard(
    modifier: Modifier,
    shape: Shape = RectangleShape,
    cardContainerColor: Color = cardColor
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(pxToDp(190)),
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = cardContainerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pxToDp(16))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(pxToDp(16))
            ) {
                CustomLabel(
                    header = "Canon EOS R50 V",
                    headerColor = darkTextColor,
                    fontSize = 16.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(pxToDp(3)))
                InfoRow(label = "Brand", value = "Canon")
                InfoRow(label = "Model", value = "EOS R5 Mark II")
                InfoRow(label = "Location", value = "IDC School of Design")
                InfoRow(label = "Timing", value = "Mon-Fri 09:00am - 05:30pm")

            }
            AppCategoryIcon(
                painter = painterResource(R.drawable.ic_favorite),
                iconDescription = "Save Icon",
                modifier = Modifier
                    .padding(pxToDp(2))
                    .align(Alignment.TopEnd),
                iconSize = pxToDp(20)
            )
        }
    }
}

    @Composable
    fun InfoRow(label: String, value: String) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomLabel(
                header = label,
                modifier = Modifier.weight(0.2f),
                headerColor = darkTextColor.copy(alpha = 0.5f),
                fontSize = 14.sp
            )
            CustomLabel(
                header = value,
                modifier = Modifier.weight(1f),
                headerColor = darkTextColor.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }


@Composable
fun InChargeCard(
    modifier: Modifier = Modifier,
    containerColor : Color = cardColor,
    shape: Shape = RectangleShape
) {
    var expanded by remember { mutableStateOf(true) }
    val iconAlignment = if (expanded) Alignment.TopEnd else Alignment.CenterEnd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(16))
        ) {
            if (expanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomLabel(
                        header = "InCharge",
                        headerColor = darkTextColor.copy(0.9f),
                        fontSize = 16.sp,
                        modifier = Modifier
                    )
                    Spacer(modifier = Modifier.height(pxToDp(5)))

                    InChargeRow(label = "Prof.", name = "Sumant Rao")
                    InChargeRow(
                        label = "Asst.",
                        name = "Akash Kumar Swami",
                        icons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(pxToDp(52)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomLabel(
                        header = "InCharge",
                        headerColor = darkTextColor.copy(0.9f),
                        fontSize = 16.sp,
                        modifier = Modifier
                    )
                }
            }


            AppCategoryIcon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                iconDescription = "Expand Icon",
                tint = darkTextColor,
                iconSize = pxToDp(20),
                modifier = Modifier
                    .align(iconAlignment)
                    .padding(pxToDp(4))
            )
        }
    }
}

@Composable
fun InChargeRow(
    label: String,
    name: String,
    icons: List<Int> = listOf(R.drawable.ic_mail)
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomLabel(
            header = label,
            headerColor = darkTextColor.copy(alpha = 0.5f),
            fontSize = 14.sp,
            modifier = Modifier.weight(0.2f)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(pxToDp(16)),
            modifier = Modifier
                .weight(0.9f)
        ) {
            CustomLabel(
                header = name,
                headerColor = darkTextColor.copy(alpha = 0.8f),
                fontSize = 14.sp,
                modifier = Modifier.padding(pxToDp(10))
            )

            icons.forEach {
                AppCircularIcon(
                    painter = painterResource(it),
                    boxSize = pxToDp(28),
                    iconPadding = pxToDp(4),
                    iconSize = pxToDp(20),
                    tint = highlightColor,
                    boxColor = circularBoxColor

                )
            }
        }

    }
}


@Composable
fun AdditionalInfoCard(
    modifier: Modifier = Modifier,
    containerColor : Color = cardColor
) {
    var expanded by remember { mutableStateOf(true) }
    val iconAlignment = if (expanded) Alignment.TopEnd else Alignment.CenterEnd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }, // collapsible behavior
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(16))
        ) {
            if (expanded) {
//                    //WHEN EXPANDED
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(pxToDp(52)),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomLabel(
                        header = "Additional Information",
                        headerColor = darkTextColor.copy(0.9f),
                        fontSize = 16.sp,
                        modifier = Modifier
                    )
                }
            }

            AppCategoryIcon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                iconDescription = "Expand Icon",
                tint = darkTextColor,
                iconSize = pxToDp(20),
                modifier = Modifier
                    .align(iconAlignment)
            )
        }
    }
}


@Composable
fun UseCard(
    modifier: Modifier = Modifier,
    containerColor : Color = cardColor
) {
    var expanded by remember { mutableStateOf(true) }
    val iconAlignment = if (expanded) Alignment.TopEnd else Alignment.CenterEnd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }, // collapsible behavior
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(16))
        ) {
            if (expanded) {
//                    //WHEN EXPANDED
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(pxToDp(52)), // Adjust height to your design
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomLabel(
                        header = "How to use",
                        headerColor = darkTextColor.copy(0.9f),
                        fontSize = 16.sp,
                        modifier = Modifier
                    )
                }
            }


            AppCategoryIcon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                iconDescription = "Expand Icon",
                tint = darkTextColor,
                iconSize = pxToDp(20),
                modifier = Modifier
                    .align(iconAlignment)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun ProductCarouselPreview() {
    val productImage = listOf(
        R.drawable.temp,
        R.drawable.temp,
        R.drawable.temp
    )
    val pagerState = rememberPagerState(pageCount = { productImage.size })
//    ProductCarousel(
//        images = productImage,
//        pagerState = pagerState
//    )
//    ProdDescScreen()
//    InChargeCard()
//    ProductDescriptionCard(modifier = Modifier)
}
//  ProdDescScreen()
//  ProductCarousel(
//      images = productImage,
//      pagerState = pagerState
//  )


//  CollapsingCard()
//}
