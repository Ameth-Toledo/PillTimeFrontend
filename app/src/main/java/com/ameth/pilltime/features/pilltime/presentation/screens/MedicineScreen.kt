package com.ameth.pilltime.features.pilltime.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ameth.pilltime.features.pilltime.presentation.viewmodels.MedicineViewModel
import com.ameth.pilltime.features.pilltime.presentation.viewmodels.MedicineViewModelFactory
import com.ameth.pilltime.features.pilltime.presentation.components.SearchMedicationCard
import com.ameth.pilltime.features.pilltime.presentation.components.ReminderCard
import com.ameth.pilltime.features.pilltime.presentation.components.MedicineItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineScreen(
    factory: MedicineViewModelFactory
) {
    val viewModel: MedicineViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "💊 MediAlert",
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                uiState.error != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = uiState.error ?: "Error desconocido",
                            color = Color.Red
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { viewModel.clearError() }) {
                            Text("Cerrar")
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        item {
                            SearchMedicationCard(
                                onSearch = { name ->
                                    viewModel.searchMedication(name)
                                }
                            )
                        }

                        item {
                            ReminderCard(
                                onSave = { name, rxcui, dosage, frequency, startTime ->
                                    viewModel.saveMedication(
                                        name, rxcui, dosage, frequency, startTime
                                    )
                                }
                            )
                        }

                        item {
                            Text(
                                text = "📋 Mis medicamentos (${uiState.medicines.size})",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        items(uiState.medicines) { medicine ->
                            MedicineItem(
                                medicine = medicine,
                                onMarkAsTaken = { id ->
                                    viewModel.markAsTaken(id)
                                },
                                onDelete = { id ->
                                    viewModel.deleteMedication(id)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}