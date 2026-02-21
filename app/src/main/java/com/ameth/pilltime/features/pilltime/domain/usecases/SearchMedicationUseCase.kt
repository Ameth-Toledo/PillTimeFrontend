package com.ameth.pilltime.features.pilltime.domain.usecases

import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.SearchResponse
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import javax.inject.Inject

class SearchMedicationUseCase @Inject constructor(
    private val repository: IMedicineRepository
) {
    suspend operator fun invoke(name: String): Result<SearchResponse> {
        return try {
            if (name.isBlank()) {
                Result.failure(Exception("El nombre no puede estar vacío"))
            } else {
                val result = repository.searchMedication(name)
                Result.success(result)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}