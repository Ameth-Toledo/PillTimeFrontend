package com.ameth.pilltime.features.pilltime.presentation.screens

import com.ameth.pilltime.features.pilltime.domain.entities.Medicine
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.SearchResultDto

data class MedicineUiState(
    val isLoading: Boolean = false,
    val medicines: List<Medicine> = emptyList(),
    val searchResults: List<SearchResultDto> = emptyList(),
    val error: String? = null,
    val isRefreshing: Boolean = false
)