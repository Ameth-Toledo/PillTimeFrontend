package com.ameth.pilltime.features.pilltime.domain.usecases

import com.ameth.pilltime.features.pilltime.domain.entities.Medicine
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import com.ameth.pilltime.features.pilltime.data.datasource.remote.mapper.toDomain

class GetMedicationByIdUseCase(
    private val repository: IMedicineRepository
) {
    suspend operator fun invoke(id: Int): Result<Medicine> {
        return try {
            val response = repository.getMedicationById(id)
            Result.success(response.medication.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}