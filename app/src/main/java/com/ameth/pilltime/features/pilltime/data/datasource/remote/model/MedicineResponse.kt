package com.ameth.pilltime.features.pilltime.data.datasource.remote.model

data class MedicationsListResponse(
    val medications: List<MedicationDto>,
    val total: Int
)

data class MedicationResponse(
    val message: String,
    val medication: MedicationDto
)

data class MedicationDto(
    val id: Int,
    val medication_name: String,
    val rxcui: String?,
    val dosage: String?,
    val frequency_hours: Int,
    val start_time: String,
    val is_active: Boolean,
    val created_at: String
)

data class SearchResponse(
    val total: Int,
    val medications: List<SearchResultDto>
)

data class SearchResultDto(
    val rxcui: String,
    val name: String,
    val synonym: String?
)

data class MessageResponse(
    val message: String
)