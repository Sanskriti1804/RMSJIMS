package com.example.labinventory.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.Typography
import com.example.labinventory.ui.theme.labelColor
import com.example.labinventory.ui.theme.searchBarColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppSearchBar(
    query : String,
    onQueryChange : (String) -> Unit,
    onSearch : (String) -> Unit,
    active : Boolean = false,
    searchIcon : Painter = painterResource(id = R.drawable.search),
    iconSize : Dp = 7.dp,
    iconDescription : String = "Search",
    containerColor : Color = searchBarColor,
    placeholder: String,
    textColor : Color = labelColor,
//    onClick: @Composable () -> Unit,
    shape: Shape = SearchBarDefaults.inputFieldShape,
    ){
    SearchBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, end = 3.dp)
            .height(16.dp),
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = active,
//        onActiveChange = { onActiveChange },
        onActiveChange = {},
        shape = shape,
        colors = SearchBarDefaults.colors(
            containerColor = containerColor
        )
    ) {
        Icon(
            painter = searchIcon,
            modifier = Modifier
                .size(iconSize)
                .padding(4.dp),
            contentDescription = iconDescription,
        )
        Text(
            text = placeholder,
            modifier = Modifier.padding(5.dp),
            style = Typography.labelSmall,
            color = textColor
        )
    }
}


