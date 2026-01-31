package com.ameth.pilltime.features.pilltime.di

import com.ameth.pilltime.core.di.AppContainer
import com.ameth.pilltime.features.pilltime.domain.usecases.GetMedicineUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.SearchMedicationUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.CreateMedicationUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.MarkAsTakenUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.DeleteMedicationUseCase
import com.ameth.pilltime.features.pilltime.presentation.viewmodels.MedicineViewModelFactory

class MedicineModule(
    private val appContainer: AppContainer
) {

    private fun provideGetMedicineUseCase(): GetMedicineUseCase {
        return GetMedicineUseCase(appContainer.medicineRepository)
    }

    private fun provideSearchMedicationUseCase(): SearchMedicationUseCase {
        return SearchMedicationUseCase(appContainer.medicineRepository)
    }

    private fun provideCreateMedicationUseCase(): CreateMedicationUseCase {
        return CreateMedicationUseCase(appContainer.medicineRepository)
    }

    private fun provideMarkAsTakenUseCase(): MarkAsTakenUseCase {
        return MarkAsTakenUseCase(appContainer.medicineRepository)
    }

    private fun provideDeleteMedicationUseCase(): DeleteMedicationUseCase {
        return DeleteMedicationUseCase(appContainer.medicineRepository)
    }

    fun provideMedicineViewModelFactory(): MedicineViewModelFactory {
        return MedicineViewModelFactory(
            getMedicineUseCase = provideGetMedicineUseCase(),
            searchMedicationUseCase = provideSearchMedicationUseCase(),
            createMedicationUseCase = provideCreateMedicationUseCase(),
            markAsTakenUseCase = provideMarkAsTakenUseCase(),
            deleteMedicationUseCase = provideDeleteMedicationUseCase()
        )
    }
}