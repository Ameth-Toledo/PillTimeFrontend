package com.ameth.pilltime.features.pilltime.domain.entities

data class Medicine(
    val id: Int,
    val medication_name: String,
    val rxcui: String?,
    val dosage: String?,
    val frequency_hours: Int,
    val start_time: String,
    val is_active: Boolean,
    val created_at: String
)