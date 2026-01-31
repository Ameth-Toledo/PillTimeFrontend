package com.ameth.pilltime.core.network

import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationRequest
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationsListResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MessageResponse
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.SearchResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PillTimeApi {

    @GET("medications/search")
    suspend fun searchMedication(@Query("name") name: String): SearchResponse

    @GET("medications")
    suspend fun getMedications(): MedicationsListResponse

    @GET("medications/{id}")
    suspend fun getMedicationById(@Path("id") id: Int): MedicationResponse

    @POST("medications")
    suspend fun createMedication(@Body medication: MedicationRequest): MedicationResponse

    @POST("medications/{id}/taken")
    suspend fun markAsTaken(@Path("id") id: Int): MessageResponse

    @DELETE("medications/{id}")
    suspend fun deleteMedication(@Path("id") id: Int): MessageResponse
}