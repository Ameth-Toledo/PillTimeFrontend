package com.ameth.pilltime.features.pilltime.presentation.utils

import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.SearchResultDto

fun extractShortName(result: SearchResultDto): String {
    val fullName = result.name

    if (fullName.contains("(") && fullName.contains(")")) {
        val parts = fullName.split("(")
        if (parts.size > 1) {
            val mainPart = parts[1].substringBefore(")")
            val components = mainPart.split("/")
            if (components.isNotEmpty()) {
                val firstComponent = components[0].trim()
                val medicineName = firstComponent.split(" ").firstOrNull() ?: firstComponent
                return medicineName.capitalize()
            }
        }
    }

    if (fullName.contains("/")) {
        val firstPart = fullName.split("/").firstOrNull()?.trim() ?: fullName
        return firstPart.split(" ").firstOrNull()?.capitalize() ?: firstPart
    }

    return fullName.split(" ").firstOrNull()?.capitalize() ?: fullName.take(30)
}

fun extractMedicationType(fullName: String): String {
    val lowerName = fullName.lowercase()

    return when {
        lowerName.contains("tablet") -> "Tableta"
        lowerName.contains("caplet") -> "Tableta"
        lowerName.contains("capsule") -> "Cápsula"
        lowerName.contains("oral solution") -> "Solución oral"
        lowerName.contains("oral suspension") -> "Suspensión oral"
        lowerName.contains("syrup") -> "Jarabe"
        lowerName.contains("pack") -> "Paquete combinado"
        lowerName.contains("injection") -> "Inyección"
        lowerName.contains("cream") -> "Crema"
        lowerName.contains("ointment") -> "Ungüento"
        lowerName.contains("gel") -> "Gel"
        lowerName.contains("powder") -> "Polvo"
        lowerName.contains("spray") -> "Spray"
        else -> "Medicamento"
    }
}

fun getCleanMedicineName(result: SearchResultDto): String {
    return extractShortName(result)
}

private fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
}