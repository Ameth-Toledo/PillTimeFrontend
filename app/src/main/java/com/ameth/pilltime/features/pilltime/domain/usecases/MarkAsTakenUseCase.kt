package com.ameth.pilltime.features.pilltime.domain.usecases

import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import javax.inject.Inject

class MarkAsTakenUseCase @Inject constructor(
    private val repository: IMedicineRepository
) {
    suspend operator fun invoke(id: Int): Result<String> {
        return try {
            val response = repository.markAsTaken(id)
            Result.success(response.message)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}