package com.ameth.pilltime.features.pilltime.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ReminderCard(
    onSave: (String, String?, String?, Int, String) -> Unit,
    modifier: Modifier = Modifier
) {
    var medicationName by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }
    var frequencyHours by remember { mutableStateOf("8") }
    var startTime by remember { mutableStateOf("08:00") }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "⏰ Programar recordatorio",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = medicationName,
                onValueChange = { medicationName = it },
                label = { Text("Nombre del medicamento") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = dosage,
                onValueChange = { dosage = it },
                label = { Text("Dosis (Ej: 500mg)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = frequencyHours,
                    onValueChange = { frequencyHours = it },
                    label = { Text("Cada (horas)") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Hora inicio") },
                    placeholder = { Text("08:00") },
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    onSave(
                        medicationName,
                        null, // rxcui
                        dosage,
                        frequencyHours.toIntOrNull() ?: 8,
                        startTime
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = medicationName.isNotBlank()
            ) {
                Text("➕ Guardar recordatorio")
            }
        }
    }
}