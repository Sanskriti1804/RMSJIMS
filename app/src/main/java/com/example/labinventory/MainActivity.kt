package com.example.labinventory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.labinventory.ui.screens.EquipmentScreen
import com.example.labinventory.ui.screens.HomeScreen
import com.example.labinventory.ui.screens.ProdDescScreen
import com.example.labinventory.ui.screens.ProductDescriptionCard
import com.example.labinventory.ui.screens.ProjectInfoScreen
import com.example.labinventory.ui.theme.LabInventoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
//            ProductDescriptionCard()
//            HomeScreen()
//            EquipmentScreen()
            ProdDescScreen()
//            ProjectInfoScreen()
//            LabInventoryTheme {
//            }
        }
    }
}
