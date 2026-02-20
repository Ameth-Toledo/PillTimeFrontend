package com.ameth.pilltime.features.pilltime.di

import com.ameth.pilltime.features.pilltime.data.repositories.MedicineRepositoryImpl
import com.ameth.pilltime.features.pilltime.domain.repositories.IMedicineRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMedicineRepository(
        impl: MedicineRepositoryImpl
    ): IMedicineRepository
}
