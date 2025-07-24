package com.example.labinventory.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.labinventory.R
import com.example.labinventory.components.AppButton
import com.example.labinventory.components.AppCategoryIcon
import com.example.labinventory.components.AppCircularIcon
import com.example.labinventory.components.CustomLabel
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay


@Composable
fun ProdDescScreen(modifier: Modifier = Modifier){
    val productImage = listOf(
        R.drawable.temp,
        R.drawable.temp,
        R.drawable.temp
    )

    val pagerState = rememberPagerState(pageCount = { productImage.size })

    val pagerInteractionSource = remember { MutableInteractionSource() }         //remember new interaction source - like user gestures
    val pagerIsPressed by pagerInteractionSource.collectIsPressedAsState()
    val pagerIsDragged by pagerState.interactionSource.collectIsDraggedAsState()

    val autoAdvance = !pagerIsDragged && !pagerIsPressed        //auto advancing when pager is not dragged or advancing

    LaunchedEffect(autoAdvance) {
        if (autoAdvance){
            while (true){
                delay(2000)
                val nextPage = (pagerState.currentPage + 1)  % productImage.size        // % productImage.size- 0 if on last page
                pagerState.animateScrollToPage(page = nextPage)
            }
        }
    }

    Column(
        modifier = Modifier.padding(5.dp)
    ) {

        ProductCarousel(
            images = productImage,
            pageInteractionSource = pagerInteractionSource,
            pagerState = pagerState, // Pass the pagerState here
        )

        ProductDescriptionCard()
        InChargeCard()
        CollapsingCard()
        AppButton(
            onClick = {},
            buttonText = "BOOK NOW"
        )
    }
}
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ProductCarousel(
    images : List<Int>,
    imageDescription : String = "Equipment images",
    contentScale: ContentScale = ContentScale.Crop,
    pagerState: PagerState, // Add PagerState as a parameter
    pageInteractionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor : Color = cardColor,
    inactiveColor : Color = Color.DarkGray,
    activeColor : Color = highlightColor,
    indicatorShape: Shape = CircleShape,
    cardPadding : Dp = 5.dp,
    prodPadding : Dp = 4.dp,
    indicatorSize : Dp = 2.dp
){
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(cardPadding)
            .background(backgroundColor)
    ){
        HorizontalPager(
            state = pagerState, // Use the passed pagerState
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.CenterHorizontally)
                .padding(top = prodPadding)
                .clickable(
                    // interactionSource allows observing and controlling the visual state of the pager (e.g., pressed, hovered).
                    interactionSource = pageInteractionSource,
                    // indication provides visual feedback for interactions (e.g., ripples on click). LocalIndication.current uses the default indication provided by the theme.
                    indication = LocalIndication.current
                ){

                }
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
        HorizontalPagerIndicator(
            pagerState = pagerState, // Use the passed pagerState
            pageCount = images.size,
            inactiveColor = inactiveColor,
            activeColor = activeColor,
            indicatorShape = indicatorShape,
            modifier = Modifier
                .align(Alignment.BottomCenter as Alignment.Horizontal)
                .padding(prodPadding)
                .size(indicatorSize)
        )
    }
}

@Composable
fun ProductDescriptionCard(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CustomLabel(
                header = "Canon EOS R50 V",
                headerColor = darkTextColor,
                modifier = Modifier
            )

            AppCategoryIcon(
                painter = painterResource(R.drawable.ic_favorite),
                iconDescription = "Save Icon",
                iconSize = 5.dp,
                modifier = Modifier.padding(1.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .padding(end = 3.dp, bottom = 5.dp)
            ){
                CustomLabel(
                    header = "Brand",
                    headerColor = darkTextColor.copy(alpha = 0.5f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )

                CustomLabel(
                    header = "Canon",
                    headerColor = darkTextColor.copy(alpha = 0.8f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .padding(end = 3.dp, bottom = 5.dp)
            ){
                CustomLabel(
                    header = "Model",
                    headerColor = darkTextColor.copy(alpha = 0.5f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )

                CustomLabel(
                    header = "EOS R5 Mark II",
                    headerColor = darkTextColor.copy(alpha = 0.8f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .padding(end = 3.dp, bottom = 5.dp)
            ){
                CustomLabel(
                    header = "Location",
                    headerColor = darkTextColor.copy(alpha = 0.5f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )

                CustomLabel(
                    header = "IDC School of Design",
                    headerColor = darkTextColor.copy(alpha = 0.8f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .padding(end = 3.dp, bottom = 6.dp)
            ){
                CustomLabel(
                    header = "Timing",
                    headerColor = darkTextColor.copy(alpha = 0.5f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )

                CustomLabel(
                    header = "Mon-Fri    09:00am - 05:30pm",
                    headerColor = darkTextColor.copy(alpha = 0.8f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
        }

    }
}


@Composable
fun InChargeCard(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 6.dp, start = 5.dp, end = 5.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(bottom = 7.dp)
        ) {
            CustomLabel(
                header = "InCharge",
                headerColor = darkTextColor.copy(0.9f),
                modifier = Modifier
            )

            AppCategoryIcon(
                painter = painterResource(R.drawable.ic_favorite),
                iconDescription = "Save Icon",
                iconSize = 6.dp,
                modifier = Modifier.padding(1.dp)
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .padding(end = 3.dp, bottom = 5.dp)
            ){
                CustomLabel(
                    header = "Prof.",
                    headerColor = darkTextColor.copy(alpha = 0.5f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
            CustomLabel(
                header = "Sumant Rao",
                headerColor = darkTextColor.copy(alpha = 0.8f),
                modifier = Modifier,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppCircularIcon(
                painter = painterResource(R.drawable.ic_mail),
                boxSize = 9.dp,
                iconPadding = 1.dp
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .width(22.dp)
                    .padding(end = 3.dp, bottom = 5.dp)
            ){
                CustomLabel(
                    header = "Asst.",
                    headerColor = darkTextColor.copy(alpha = 0.5f),
                    modifier = Modifier,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(3.dp))
            CustomLabel(
                header = "Akash Kumar Swami",
                headerColor = darkTextColor.copy(alpha = 0.8f),
                modifier = Modifier,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppCircularIcon(
                painter = painterResource(R.drawable.ic_mail),
                boxSize = 9.dp,
                iconPadding = 1.dp
            )
            Spacer(modifier = Modifier.width(4.dp))
            AppCircularIcon(
                painter = painterResource(R.drawable.ic_call),
                boxSize = 9.dp,
                iconPadding = 1.dp
            )
        }
    }
}

@Composable
fun CollapsingCard(){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 6.dp, bottom = 6.dp, start = 5.dp, end = 7.dp)
    ) {
        CustomLabel(
            header = "Additional Information",
            fontSize = 14.sp,
            headerColor = darkTextColor.copy(0.9f),
            modifier = Modifier
        )

        AppCategoryIcon(
            painter = painterResource(R.drawable.ic_arrow_down),
            iconDescription = "Expand Icon"
        )
    }
}
