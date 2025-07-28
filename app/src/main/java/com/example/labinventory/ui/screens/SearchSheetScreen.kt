package com.example.labinventory.ui.screens

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.aiColor
import com.example.labinventory.ui.theme.navBackColor
import com.example.labinventory.ui.theme.someGrayColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.SearchViewModel

@Composable
fun SearchBottomSheet(
    viewModel: SearchViewModel,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    val query by viewModel.query

    Surface(
        shape = RoundedCornerShape(topStart = pxToDp(30), topEnd = pxToDp(30)),
        color = navBackColor,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = pxToDp(880))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(pxToDp(15)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Icons Row
            Box(modifier = Modifier.fillMaxWidth()) {
                Icon(
                    painter = painterResource(R.drawable.ic_chat),
                    contentDescription = "Chat Icon",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .size(pxToDp(44))
                )

                IconButton(
                    onClick = onClose,
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = "Close",
                        modifier = Modifier.size(pxToDp(26)),
                        tint = someGrayColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(pxToDp(12)))

            Text(
                text = "Share your project to get",
                color = aiColor,
                fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                textAlign = TextAlign.Center
            )
            Text(
                text = "equipment suggestions",
                fontSize = 16.sp,
                color = aiColor,
                fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

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
            .padding(vertical = 8.dp),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(onGo = {
            onSearch()
//            LocalSoftwareKeyboardController.current?.hide()
        }),
        colors = OutlinedTextFieldDefaults.colors()
    )
}


@Preview(showBackground = true)
@Composable
fun SearchBottomSheetPreview() {
    val viewModel = remember { SearchViewModel() }
    SearchBottomSheet(
        viewModel = viewModel,
        onClose = {}
    )
}
