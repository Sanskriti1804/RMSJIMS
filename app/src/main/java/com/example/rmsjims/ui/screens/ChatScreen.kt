package com.example.rmsjims.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R
import com.example.rmsjims.ui.theme.aiColor
import com.example.rmsjims.ui.theme.navBackColor
import com.example.rmsjims.ui.theme.someGrayColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.SearchViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun ChatBottomSheet(
//    navController: NavHostController,
    viewModel: SearchViewModel = koinViewModel()
) {
    val query by viewModel.query

    Surface(
        shape = RoundedCornerShape(
            topStart = ResponsiveLayout.getResponsiveSize(30.dp, 36.dp, 42.dp), 
            topEnd = ResponsiveLayout.getResponsiveSize(30.dp, 36.dp, 42.dp)
        ),
        color = navBackColor,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = ResponsiveLayout.getResponsiveSize(880.dp, 920.dp, 960.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(15.dp, 18.dp, 22.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Icons Row
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(R.drawable.ic_chat),
                    contentDescription = "Chat Icon",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(ResponsiveLayout.getResponsiveSize(44.dp, 48.dp, 52.dp))
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Close",
                        modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(26.dp, 28.dp, 32.dp)),
                        tint = someGrayColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 14.dp, 16.dp)))

            Text(
                text = "Share your project to get",
                color = aiColor,
                fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                textAlign = TextAlign.Center
            )
            Text(
                text = "equipment suggestions",
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                color = aiColor,
                fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(24.dp, 28.dp, 32.dp)))

            // Input Field
            SearchInputField(
                query = query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = viewModel::onSearchTriggered
            )
        }
    }
}


@Composable
fun SearchInputField(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        label = { Text("Enter keyword") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp)),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(onGo = {
            onSearch()
//            LocalSoftwareKeyboardController.current?.hide()
        }),
        colors = OutlinedTextFieldDefaults.colors()
    )
}

//
//@Preview(showBackground = true)
//@Composable
//fun SearchBottomSheetPreview() {
//    val viewModel = remember { SearchViewModel() }
//    ChatBottomSheet(
//        viewModel = viewModel,
//        onClose = {}
//    )
//}
