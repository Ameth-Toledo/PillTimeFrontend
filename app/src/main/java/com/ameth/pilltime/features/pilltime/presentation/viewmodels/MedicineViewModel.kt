package com.ameth.pilltime.features.pilltime.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ameth.pilltime.features.pilltime.domain.usecases.GetMedicineUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.SearchMedicationUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.CreateMedicationUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.MarkAsTakenUseCase
import com.ameth.pilltime.features.pilltime.domain.usecases.DeleteMedicationUseCase
import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationRequest
import com.ameth.pilltime.features.pilltime.presentation.screens.MedicineUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MedicineViewModel(
    private val getMedicineUseCase: GetMedicineUseCase,
    private val searchMedicationUseCase: SearchMedicationUseCase,
    private val createMedicationUseCase: CreateMedicationUseCase,
    private val markAsTakenUseCase: MarkAsTakenUseCase,
    private val deleteMedicationUseCase: DeleteMedicationUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MedicineUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMedicines()
    }

    private fun loadMedicines() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            val result = getMedicineUseCase()
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { list ->
                        currentState.copy(
                            isLoading = false,
                            medicines = list,
                            error = null
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            medicines = emptyList(),
                            error = null
                        )
                    }
                )
            }
        }
    }

    fun searchMedication(name: String) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = searchMedicationUseCase(name)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { searchResponse ->
                        currentState.copy(
                            isLoading = false,
                            error = null,
                            searchResults = searchResponse.medications
                        )
                    },
                    onFailure = { error ->
                        currentState.copy(
                            isLoading = false,
                            searchResults = emptyList(),
                            error = null
                        )
                    }
                )
            }
        }
    }

    fun clearSearchResults() {
        _uiState.update { it.copy(searchResults = emptyList()) }
    }

    fun saveMedication(
        medicationName: String,
        rxcui: String?,
        dosage: String?,
        frequencyHours: Int,
        startTime: String
    ) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val request = MedicationRequest(
                medication_name = medicationName,
                rxcui = rxcui,
                dosage = dosage,
                frequency_hours = frequencyHours,
                start_time = startTime
            )

            val result = createMedicationUseCase(request)
            _uiState.update { currentState ->
                result.fold(
                    onSuccess = { medicine ->
                        loadMedicines()
                        currentState.copy(isLoading = false, error = null)
                    },
                    onFailure = { error ->
                        currentState.copy(isLoading = false, error = error.message)
                    }
                )
            }
        }
    }

    fun markAsTaken(medicationId: Int) {
        viewModelScope.launch {
            val result = markAsTakenUseCase(medicationId)
            result.fold(
                onSuccess = { message ->
                    loadMedicines()
                },
                onFailure = { error ->
                }
            )
        }
    }

    fun deleteMedication(medicationId: Int) {
        viewModelScope.launch {
            val result = deleteMedicationUseCase(medicationId)
            result.fold(
                onSuccess = { message ->
                    loadMedicines()
                },
                onFailure = { error ->
                }
            )
        }
    }

    fun refresh() {
        loadMedicines()
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}