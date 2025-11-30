package com.example.rmsjims.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppDropDownTextField
import com.example.rmsjims.ui.components.AppTextField
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.pxToDp
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.ui.components.rememberImagePicker
import android.net.Uri
import coil.compose.AsyncImage
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip

@SuppressLint("RememberReturnType")
@Composable
fun NewEquipmentScreen() {
    var value by remember { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<String?>(null) }
    val fontSize = 14.sp

    Scaffold(
        topBar = {
            CustomTopBar(title = "Add Equipment")
        },
        containerColor = whiteColor,
        bottomBar = {
            AppButton(
                buttonText = "ADD EQUIPMENT",
                onClick = {},
                modifier = Modifier.padding(ResponsiveLayout.getResponsivePadding(1.dp, 2.dp, 3.dp))
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(ResponsiveLayout.getHorizontalPadding())
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(13.dp, 16.dp, 20.dp))
        ) {
            // 🔵 Image Upload Card
            AddImageCard(
                selectedImageUri = selectedImageUri,
                onImageSelected = { uri ->
                    selectedImageUri = uri?.toString()
                }
            )

            // 🟠 Fields follow same pattern
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Equipment Name"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Brand"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Model"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Location"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Incharge (prof)"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Incharge (Assisstant)"
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Additional Information",
            )
            AppTextField(
                value = value,
                onValueChange = { value = it },
                placeholder = "Link (How to use video)"
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(4))
            ) {
                AppDropDownTextField(
                    modifier = Modifier.weight(1f),
                    value = value,
                    onValueChange = { value = it },
                    placeholder = "General Category",
                    items = listOf("Yes", "No")
                )
                AppDropDownTextField(
                    modifier = Modifier.weight(1f),
                    value = value,
                    onValueChange = { value = it },
                    placeholder = "Sub Category",
                    items = listOf("Yes", "No")
                )
            }
        }
    }
}

@Composable
fun AddImageCard(
    modifier: Modifier = Modifier,
    selectedImageUri: String? = null,
    onImageSelected: (Uri?) -> Unit
) {
    val imagePicker = rememberImagePicker(onImageSelected = onImageSelected)
    
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(pxToDp(108)),
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        onClick = {
            imagePicker.onPickFromGallery()
        },
        shape = RoundedCornerShape(pxToDp(4))
    ) {
        if (selectedImageUri != null) {
            try {
                val uri = Uri.parse(selectedImageUri)
                AsyncImage(
                    model = uri,
                    contentDescription = "Selected Image",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(pxToDp(4))),
                    contentScale = ContentScale.Crop
                )
            } catch (e: Exception) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_image),
                        contentDescription = "Add Image",
                        modifier = Modifier.size(pxToDp(37)),
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    CustomLabel(
                        header = "Add Image",
                        fontSize = 12.sp,
                        headerColor = onSurfaceColor.copy(0.7f)
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_image),
                    contentDescription = "Add Image",
                    modifier = Modifier.size(pxToDp(37)),
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomLabel(
                    header = "Add Image",
                    fontSize = 12.sp,
                    headerColor = onSurfaceColor.copy(0.7f)
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddEquipmentScreenPreview() {
    NewEquipmentScreen()
}
