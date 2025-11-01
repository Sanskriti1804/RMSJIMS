package com.example.rmsjims.ui.screens.assisstant

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun NewEquipmentScreen(
    navController: NavHostController
) {
    // Placeholder new equipment entries
    val newEquipment = listOf(
        EquipmentEntry(
            name = "Microscope Advanced Pro",
            category = "Microscopy",
            manufacturer = "TechCorp Industries",
            modelNumber = "MC-2024-PRO",
            serialNumber = "SN-2024-MC-001",
            purchaseDate = "2024-01-15",
            purchasePrice = "₹85,000",
            status = "Pending Registration"
        ),
        EquipmentEntry(
            name = "Centrifuge Ultra",
            category = "Lab Equipment",
            manufacturer = "LabTech Solutions",
            modelNumber = "CF-2024-ULTRA",
            serialNumber = "SN-2024-CF-002",
            purchaseDate = "2024-01-18",
            purchasePrice = "₹45,000",
            status = "Pending Registration"
        ),
        EquipmentEntry(
            name = "DNA Sequencer",
            category = "Biotechnology",
            manufacturer = "BioGen Systems",
            modelNumber = "DS-2024-BIO",
            serialNumber = "SN-2024-DS-003",
            purchaseDate = "2024-01-20",
            purchasePrice = "₹2,50,000",
            status = "Pending Registration"
        )
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "New Equipment",
                onNavigationClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Add New Button
            AppButton(
                buttonText = "+ Add New Equipment",
                onClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding())
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Summary
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                colors = CardDefaults.cardColors(
                    containerColor = onSurfaceVariant
                ),
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = ResponsiveLayout.getHorizontalPadding(),
                        vertical = ResponsiveLayout.getVerticalPadding()
                    )
                ) {
                    CustomLabel(
                        header = "Pending Registrations",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                        headerColor = onSurfaceColor.copy(0.7f)
                    )
                    Spacer(modifier = Modifier.height(pxToDp(8)))
                    CustomLabel(
                        header = "${newEquipment.size} Items",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                        headerColor = primaryColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Equipment List
            androidx.compose.foundation.lazy.LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                items(newEquipment) { equipment ->
                    EquipmentEntryCard(equipment = equipment)
                }
            }
        }
    }
}

@Composable
fun EquipmentEntryCard(equipment: EquipmentEntry) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
            ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            CustomLabel(
                header = equipment.name,
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                headerColor = onSurfaceColor
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                DetailRoww("Category", equipment.category)
                DetailRoww("Manufacturer", equipment.manufacturer)
                DetailRoww("Model Number", equipment.modelNumber)
                DetailRoww("Serial Number", equipment.serialNumber)
                DetailRoww("Purchase Date", equipment.purchaseDate)
                DetailRoww("Purchase Price", equipment.purchasePrice)
                DetailRoww("Status", equipment.status)
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "Register",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
                AppButton(
                    buttonText = "Edit",
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    containerColor = onSurfaceVariant,
                    contentColor = onSurfaceColor
                )
            }
        }
    }
}

//@Composable
//fun DetailRow(label: String, value: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        CustomLabel(
//            header = "$label:",
//            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
//            headerColor = onSurfaceColor.copy(0.7f)
//        )
//        CustomLabel(
//            header = value,
//            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
//            headerColor = onSurfaceColor
//        )
//    }
//}

// Placeholder data class
data class EquipmentEntry(
    val name: String,
    val category: String,
    val manufacturer: String,
    val modelNumber: String,
    val serialNumber: String,
    val purchaseDate: String,
    val purchasePrice: String,
    val status: String
)

@Preview(showBackground = true)
@Composable
fun NewEquipmentScreenPreview() {
    val navController = rememberNavController()
    NewEquipmentScreen(navController = navController)
}
