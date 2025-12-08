package com.example.rmsjims.ui.screens

import androidx.compose.foundation.LocalIndication
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.rmsjims.R
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppCategoryIcon
import com.example.rmsjims.ui.components.AppCircularIcon
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.circularBoxColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.editCardTextColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.errorColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp
import com.example.rmsjims.viewmodel.FacilitiesViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPagerIndicator
import kotlinx.coroutines.delay
import org.koin.androidx.compose.koinViewModel

@Composable
fun ProdDescScreen(
    sessionViewModel: UserSessionViewModel = koinViewModel(),
    navController: NavHostController
) {
    val userRole = sessionViewModel.userRole

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
            CustomTopBar(
                title = "Camera",
                onNavigationClick = {
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            if (userRole == UserRole.ASSISTANT){
                AppButton(
                    onClick = {
                        navController.navigate(Screen.CalendarScreen.route)
                    },
                    buttonText = "BOOK NOW",
                    modifier = Modifier.padding(ResponsiveLayout.getHorizontalPadding())
                )
            }
            else{
                ActionCard(
                    onEditClick = {},
                    onDeleteClick = {},
                    onBookClick = {},
                    modifier = Modifier.padding(ResponsiveLayout.getHorizontalPadding())
                )
            }
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(ResponsiveLayout.getHorizontalPadding()),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp))
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(20.dp, 24.dp, 28.dp)))
            ProductCarousel(
                images = productImage,
                pageInteractionSource = pagerInteractionSource,
                pagerState = pagerState
            )

            ProductDescriptionCard(modifier = Modifier)

            InChargeCard()
            AdditionalInfoCard()
            UseCard()
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
    backgroundColor : Color = onSurfaceVariant,
    inactiveColor : Color = Color.DarkGray,
    activeColor : Color = primaryColor,
    indicatorShape: Shape = CircleShape,
    indicatorSize : Dp = ResponsiveLayout.getResponsiveSize(6.dp, 8.dp, 10.dp),
    isFav : Boolean = false
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(ResponsiveLayout.getResponsiveSize(200.dp, 240.dp, 280.dp)),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        )
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp))
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
    cardContainerColor: Color = onSurfaceVariant
) {
    val facilitiesViewModel : FacilitiesViewModel = koinViewModel ()
    val facilitiesList = facilitiesViewModel.facilitiesState

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
                    headerColor = onSurfaceColor,
                    fontSize = 16.sp,
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.height(pxToDp(3)))

                when(facilitiesList){
                    is UiState.Loading -> {
                        Text("Loading facilities")
                    }
                    is UiState.Error -> {
                        Text("Error loading facilities")
                    }
                    is UiState.Success -> {
                        val currentFacility = facilitiesList.data.firstOrNull()

                        if (currentFacility != null){
                            InfoRow(label = "Brand", value = "Canon")
                            InfoRow(label = "Model", value = "EOS R5 Mark II")
                            InfoRow(label = "Location", value = currentFacility.location)
                            InfoRow(label = "Timing", value = currentFacility.timings)
                        }
                    }
                }
            }
            AppCategoryIcon(
                painter = painterResource(R.drawable.ic_favorite),
                iconDescription = "Save Icon",
                modifier = Modifier
                    .padding(pxToDp(2))
                    .align(Alignment.TopEnd),
                iconSize = pxToDp(20),
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
                headerColor = onSurfaceColor.copy(alpha = 0.5f),
                fontSize = 14.sp
            )
            CustomLabel(
                header = value,
                modifier = Modifier.weight(1f),
                headerColor = onSurfaceColor.copy(alpha = 0.8f),
                fontSize = 14.sp
            )
        }
    }


@Composable
fun InChargeCard(
    modifier: Modifier = Modifier,
    containerColor : Color = onSurfaceVariant,
    shape: Shape = RectangleShape
) {
    val facilitiesViewModel : FacilitiesViewModel = koinViewModel()
    val facilitiesList = facilitiesViewModel.facilitiesState

    var expanded by remember { mutableStateOf(true) }
    val iconAlignment = if (expanded) Alignment.TopEnd else Alignment.CenterEnd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = shape
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(16))
        ) {
            if (expanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(pxToDp(12)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when(facilitiesList){
                        is UiState.Loading -> Text("Loading facilities")
                        is UiState.Error -> Text("Error loading facilities")
                        is UiState.Success -> {
                            val currentFacility = facilitiesList.data.firstOrNull()
                            if (currentFacility != null){
                                CustomLabel(
                                    header = "InCharge",
                                    headerColor = onSurfaceColor.copy(0.9f),
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(pxToDp(5)))
                                InChargeRow(
                                    label = "Prof.",
                                    name = currentFacility.prof_incharge,
                                    email = currentFacility.prof_incharge_email
                                )
                                InChargeRow(
                                    label = "Asst.",
                                    name = currentFacility.lab_incharge,
                                    icons = listOf(R.drawable.ic_mail, R.drawable.ic_call),
                                    email = currentFacility.lab_incharge_email,
                                    phone = currentFacility.lab_incharge_phone
                                )
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomLabel(
                        header = "InCharge",
                        headerColor = onSurfaceColor.copy(0.9f),
                        fontSize = 16.sp
                    )
                }
            }

            AppCategoryIcon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                iconDescription = "Expand Icon",
                tint = onSurfaceColor,
                iconSize = pxToDp(20),
                modifier = Modifier.align(iconAlignment).padding(pxToDp(4))
            )
        }
    }
}


@Composable
fun InChargeRow(
    label: String,
    name: String,
    icons: List<Int> = listOf(R.drawable.ic_mail),
    email: String? = null,
    phone: String? = null
) {
    val context = LocalContext.current
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomLabel(
            header = label,
            headerColor = onSurfaceColor.copy(alpha = 0.5f),
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
                headerColor = onSurfaceColor.copy(alpha = 0.8f),
                fontSize = 14.sp,
                modifier = Modifier.padding(pxToDp(10))
            )

            icons.forEach { iconRes ->
                val adjustedIconSize = if (iconRes == R.drawable.ic_call) pxToDp(16) else pxToDp(20)
                AppCircularIcon(
                    painter = painterResource(iconRes),
                    boxSize = pxToDp(28),
                    iconPadding = pxToDp(4),
                    iconSize = adjustedIconSize,
                    tint = primaryColor,
                    boxColor = circularBoxColor,
                    onClick = {
                        when (iconRes) {
                            R.drawable.ic_mail -> {
                                val emailAddress = email ?: "sumant.rao@jims.edu.in"
                                // Open mail app with email in 'To' field
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:$emailAddress")
                                }
                                context.startActivity(intent)
                            }
                            R.drawable.ic_call -> {
                                phone?.let { phoneNumber ->
                                    // Open dialer app with number on keypad
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:$phoneNumber")
                                    }
                                    context.startActivity(intent)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AdditionalInfoCard(
    modifier: Modifier = Modifier,
    containerColor : Color = onSurfaceVariant
) {
    val facilitiesViewModel : FacilitiesViewModel = koinViewModel()
    val facilitiesList = facilitiesViewModel.facilitiesState

    var expanded by remember { mutableStateOf(true) }
    val iconAlignment = if (expanded) Alignment.TopEnd else Alignment.CenterEnd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(16))
        ) {
            if (expanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(pxToDp(12)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    when(facilitiesList){
                        is UiState.Loading -> Text("Loading facilities")
                        is UiState.Error -> Text("Error loading facilities")
                        is UiState.Success -> {
                            val currentFacility = facilitiesList.data.firstOrNull()
                            if (currentFacility != null){
                                CustomLabel(
                                    header = "Additional Information",
                                    headerColor = onSurfaceColor.copy(0.9f),
                                    fontSize = 16.sp
                                )
                                Spacer(modifier = Modifier.height(pxToDp(5)))
                                CustomLabel(
                                    header = currentFacility.description ?: "No Description found"
                                )
                            }
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomLabel(
                        header = "Additional Information",
                        headerColor = onSurfaceColor.copy(0.9f),
                        fontSize = 16.sp
                    )
                }
            }

            AppCategoryIcon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                iconDescription = "Expand Icon",
                tint = onSurfaceColor,
                iconSize = pxToDp(20),
                modifier = Modifier.align(iconAlignment).padding(pxToDp(4))
            )
        }
    }
}


@Composable
fun UseCard(
    modifier: Modifier = Modifier,
    containerColor: Color = onSurfaceVariant
) {
    var expanded by remember { mutableStateOf(true) }
    val iconAlignment = if (expanded) Alignment.TopEnd else Alignment.CenterEnd

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(containerColor = containerColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(16))
        ) {
            if (expanded) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(pxToDp(8)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomLabel(
                        header = "How to use",
                        headerColor = onSurfaceColor.copy(0.9f),
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(pxToDp(5)))
                    // Add the actual usage instructions here
                    Text("No usage instructions available") // Placeholder
                }
            } else {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterStart
                ) {
                    CustomLabel(
                        header = "How to use",
                        headerColor = onSurfaceColor.copy(0.9f),
                        fontSize = 16.sp
                    )
                }
            }

            AppCategoryIcon(
                painter = painterResource(
                    if (expanded) R.drawable.ic_arrow_up else R.drawable.ic_arrow_down
                ),
                iconDescription = "Expand Icon",
                tint = onSurfaceColor,
                iconSize = pxToDp(20),
                modifier = Modifier.align(iconAlignment).padding(pxToDp(4))
            )
        }
    }
}

@Composable
fun ActionCard(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBookClick: () -> Unit,
    modifier: Modifier
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(18))
            ) {
                AppButton(
                    onClick = onEditClick,
                    containerColor = onSurfaceVariant,
                    contentColor = editCardTextColor,
                    buttonText = "EDIT"
                )
                AppButton(
                    onClick = onDeleteClick,
                    containerColor = onSurfaceVariant,
                    contentColor = errorColor,
                    buttonText = "DELETE"
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                onClick = onBookClick,
                buttonText = "BOOK"
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
////    ProductCarousel(
////        images = productImage,
////        pagerState = pagerState
////    )
//    ProdDescScreen()
////    InChargeCard()
////    ProductDescriptionCard(modifier = Modifier)
//}
////  ProdDescScreen()
////  ProductCarousel(
////      images = productImage,
////      pagerState = pagerState
////  )
////  CollapsingCard()
}
