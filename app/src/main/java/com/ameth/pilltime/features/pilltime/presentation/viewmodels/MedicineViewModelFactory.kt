package com.ameth.pilltime.features.pilltime.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ameth.pilltime.features.pilltime.domain.usecases.GetMedicineUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.SearchMedicationUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.CreateMedicationUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.MarkAsTakenUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.DeleteMedicationUseCase

class MedicineViewModelFactory(
    private val getMedicineUseCase: GetMedicineUseCase,
    private val searchMedicationUseCase: SearchMedicationUseCase,
    private val createMedicationUseCase: CreateMedicationUseCase,
    private val markAsTakenUseCase: MarkAsTakenUseCase,
    private val deleteMedicationUseCase: DeleteMedicationUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MedicineViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MedicineViewModel(
                getMedicineUseCase,
                searchMedicationUseCase,
                createMedicationUseCase,
                markAsTakenUseCase,
                deleteMedicationUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}