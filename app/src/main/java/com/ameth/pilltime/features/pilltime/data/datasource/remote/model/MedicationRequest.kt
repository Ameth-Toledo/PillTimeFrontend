package com.ameth.pilltime.features.pilltime.data.datasource.remote.model

data class MedicationRequest(
    val medication_name: String,
    val rxcui: String?,
    val dosage: String?,
    val frequency_hours: Int,
    val start_time: String
)