package com.ameth.pilltime.features.pilltime.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.outlined.Medication
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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

    // Estado para el medicamento seleccionado de la búsqueda
    var selectedMedicationName by remember { mutableStateOf("") }
    var selectedRxcui by remember { mutableStateOf<String?>(null) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Icons.Default.Medication,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            "PillTime",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when {
                // Solo mostrar loading en la carga inicial
                uiState.isLoading && uiState.medicines.isEmpty() -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary,
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "Cargando medicamentos...",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
                // Solo mostrar error si es un error REAL (no "no hay medicamentos")
                uiState.error != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = RoundedCornerShape(32.dp),
                            color = MaterialTheme.colorScheme.errorContainer,
                            modifier = Modifier.size(120.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(56.dp),
                                    tint = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = "Error al cargar",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = uiState.error ?: "Error desconocido",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(
                                onClick = { viewModel.clearError() },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text("Cerrar")
                            }
                            Button(
                                onClick = { viewModel.refresh() },
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Icon(
                                    Icons.Default.Refresh,
                                    contentDescription = null,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Reintentar")
                            }
                        }
                    }
                }
                // Estado normal: mostrar pantalla con formulario y lista (puede estar vacía)
                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(bottom = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(0.dp)
                    ) {
                        // Search card
                        item {
                            Box(modifier = Modifier.padding(16.dp)) {
                                SearchMedicationCard(
                                    onSearch = { name ->
                                        viewModel.searchMedication(name)
                                    },
                                    searchResults = uiState.searchResults,
                                    onSelectMedication = { result ->
                                        selectedMedicationName = result.name
                                        selectedRxcui = result.rxcui
                                        viewModel.clearSearchResults()
                                    },
                                    onClearResults = {
                                        viewModel.clearSearchResults()
                                    }
                                )
                            }
                        }

                        // Reminder card
                        item {
                            Box(modifier = Modifier.padding(horizontal = 16.dp)) {
                                ReminderCard(
                                    medicationName = selectedMedicationName,
                                    onMedicationNameChange = { selectedMedicationName = it },
                                    onSave = { name, rxcui, dosage, frequency, startTime ->
                                        viewModel.saveMedication(
                                            name,
                                            rxcui ?: selectedRxcui,
                                            dosage,
                                            frequency,
                                            startTime
                                        )
                                        // Limpiar después de guardar
                                        selectedMedicationName = ""
                                        selectedRxcui = null
                                    }
                                )
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // My medications header
                        item {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Mis medicamentos",
                                    style = MaterialTheme.typography.headlineSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )

                                Surface(
                                    shape = RoundedCornerShape(12.dp),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = "${uiState.medicines.size}",
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }
                            }
                        }

                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        // Empty state - DISEÑO MODERNO
                        if (uiState.medicines.isEmpty()) {
                            item {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 32.dp, vertical = 48.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    // Icon with background
                                    Surface(
                                        shape = RoundedCornerShape(32.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        modifier = Modifier.size(120.dp)
                                    ) {
                                        Box(
                                            contentAlignment = Alignment.Center,
                                            modifier = Modifier.fillMaxSize()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Outlined.Medication,
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp),
                                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(32.dp))

                                    // Title
                                    Text(
                                        text = "No tienes medicamentos",
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground,
                                        textAlign = TextAlign.Center
                                    )

                                    Spacer(modifier = Modifier.height(12.dp))

                                    // Description
                                    Text(
                                        text = "Agrega tu primer medicamento usando el formulario de arriba para comenzar a llevar un control de tus tomas",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }

                        // Medications list
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