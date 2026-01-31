package com.ameth.pilltime.core.di

import android.content.Context
import com.ameth.pilltime.core.network.PillTimeApi
import com.ameth.pilltime.features.pilltime.data.repositories.MedicineRepositoryImpl
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer(context: Context) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://pilltime.onrender.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val pillTimeApi: PillTimeApi by lazy {
        retrofit.create(PillTimeApi::class.java)
    }

    val medicineRepository: IMedicineRepository by lazy {
        MedicineRepositoryImpl(pillTimeApi)
    }
}