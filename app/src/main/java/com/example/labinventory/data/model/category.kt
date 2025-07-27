package com.example.labinventory.data.model

import androidx.annotation.DrawableRes
import com.example.labinventory.R

data class EquipmentCategory(
    @DrawableRes val categoryImage: Int,
    val label : String
)


val categories = listOf(
    EquipmentCategory(R.drawable.ic_vector, "All"),
    EquipmentCategory(R.drawable.ic_camera, "Camera"),
    EquipmentCategory(R.drawable.ic_lens, "Lens"),
    EquipmentCategory(R.drawable.ic_light, "Light"),
    EquipmentCategory(R.drawable.ic_storage, "Storage"),
    EquipmentCategory(R.drawable.ic_tripod, "Tripod"),
)