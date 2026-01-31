package com.ameth.pilltime.features.pilltime.domain.usecases

import com.ameth.pilltime.features.pilltime.domain.entities.Medicine
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import com.ameth.pilltime.features.pilltime.data.datasource.remote.mapper.toDomain

class GetMedicineUseCase(
    private val repository: IMedicineRepository
) {
    suspend operator fun invoke(): Result<List<Medicine>> {
        return try {
            val response = repository.getMedications()
            val medicines = response.medications.map { it.toDomain() }

            if (medicines.isEmpty()) {
                Result.failure(Exception("No se encontraron medicamentos"))
            } else {
                Result.success(medicines)
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}