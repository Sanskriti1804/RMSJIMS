package com.example.rmsjims.data.model

import androidx.annotation.DrawableRes
import com.example.rmsjims.R

data class EquipmentCategory(
    val id : Int,
    @DrawableRes val categoryImage: Int,
    val label : String
)


val categories = listOf(
    EquipmentCategory(1,R.drawable.ic_vector, "All"),
    EquipmentCategory(2,R.drawable.ic_camera, "Camera"),
    EquipmentCategory(3,R.drawable.ic_lens, "Lens"),
    EquipmentCategory(4,R.drawable.ic_light, "Light"),
    EquipmentCategory(5,R.drawable.ic_storage, "Storage"),
    EquipmentCategory(6,R.drawable.ic_tripod, "Tripod"),
)