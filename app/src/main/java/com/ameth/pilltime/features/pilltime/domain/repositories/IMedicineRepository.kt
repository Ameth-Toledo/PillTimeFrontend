package com.ameth.pilltime.features.pilltime.domain.repositories

import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationRequest
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationsListResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MessageResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.SearchResponse

interface IMedicineRepository {
    suspend fun searchMedication(name: String): SearchResponse
    suspend fun getMedications(): MedicationsListResponse
    suspend fun getMedicationById(id: Int): MedicationResponse
    suspend fun createMedication(medication: MedicationRequest): MedicationResponse
    suspend fun markAsTaken(id: Int): MessageResponse
    suspend fun deleteMedication(id: Int): MessageResponse
}