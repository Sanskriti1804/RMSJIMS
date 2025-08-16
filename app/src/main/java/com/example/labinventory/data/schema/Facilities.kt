package com.example.labinventory.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Facilities(
    val id : Int,
    val name : String,
    val department_id : Int,
    val type : String,
    val location : String,
    val timings : String,
    val lab_incharge : String,
    val lab_incharge_phone : String,
    val lab_incharge_email : String ?= null,
    val prof_incharge : String,
    val prof_incharge_email : String,
    val description : String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    val branch_id : Int

)