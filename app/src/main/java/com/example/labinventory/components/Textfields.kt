package com.example.labinventory.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.darkTextColor

@Composable
fun AppTextField(
    value : String,
    onValueChange : (String) -> Unit,
    shape: Shape = RoundedCornerShape(1.dp),
    placeholder: String,
    textColor : Color = darkTextColor.copy(alpha = 0.7f),
    containercolor : Color = cardColor,
    maxlines : Int = 1,
    visualTransformation : VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        placeholder = {
            Text(
                text = placeholder,
                modifier = Modifier
                    .padding(4.dp),
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedContainerColor = containercolor,
            unfocusedContainerColor = containercolor,
            focusedIndicatorColor = textColor
        ),
        visualTransformation = visualTransformation,
        maxLines = maxlines

    )
}


@Composable
fun AppDropDownTextField(
    value : String,
    onValueChange : (String) -> Unit,
    shape: Shape = RoundedCornerShape(1.dp),
    placeholder: String,
    textColor : Color = darkTextColor.copy(alpha = 0.7f),
    containercolor : Color = cardColor,
    maxlines : Int = 1,
    visualTransformation : VisualTransformation = VisualTransformation.None
){
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        placeholder = {
            Row {
                Text(
                    text = placeholder,
                    modifier = Modifier
                        .padding(4.dp),
                )
                AppCategoryIcon(
                    painter = painterResource(R.drawable.ic_dropdown),
                    iconDescription = "Drop Down Icon",
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            focusedContainerColor = containercolor,
            unfocusedContainerColor = containercolor,
            focusedIndicatorColor = textColor
        ),
        visualTransformation = visualTransformation,
        maxLines = maxlines

    )
}


