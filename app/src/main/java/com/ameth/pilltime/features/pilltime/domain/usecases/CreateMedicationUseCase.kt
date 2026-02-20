package com.ameth.pilltime.features.pilltime.domain.usecases

import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationRequest
import com.ameth.pilltime.features.pilltime.domain.entities.Medicine
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import com.ameth.pilltime.features.pilltime.data.datasource.remote.mapper.toDomain
import javax.inject.Inject

class CreateMedicationUseCase @Inject constructor(
    private val repository: IMedicineRepository
) {
    suspend operator fun invoke(medication: MedicationRequest): Result<Medicine> {
        return try {
            val response = repository.createMedication(medication)
            Result.success(response.medication.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}