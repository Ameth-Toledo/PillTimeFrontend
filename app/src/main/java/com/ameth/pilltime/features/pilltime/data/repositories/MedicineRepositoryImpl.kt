package com.ameth.pilltime.features.pilltime.data.repositories

import com.ameth.pilltime.core.network.PillTimeApi
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationRequest
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationsListResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MessageResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.SearchResponse
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository

class MedicineRepositoryImpl(
    private val api: PillTimeApi
) : IMedicineRepository {

    override suspend fun searchMedication(name: String): SearchResponse {
        return api.searchMedication(name)
    }

    override suspend fun getMedications(): MedicationsListResponse {
        return api.getMedications()
    }

    override suspend fun getMedicationById(id: Int): MedicationResponse {
        return api.getMedicationById(id)
    }

    override suspend fun createMedication(medication: MedicationRequest): MedicationResponse {
        return api.createMedication(medication)
    }

    override suspend fun markAsTaken(id: Int): MessageResponse {
        return api.markAsTaken(id)
    }

    override suspend fun deleteMedication(id: Int): MessageResponse {
        return api.deleteMedication(id)
    }
}