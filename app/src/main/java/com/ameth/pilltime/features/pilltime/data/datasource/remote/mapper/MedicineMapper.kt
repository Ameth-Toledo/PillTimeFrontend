package com.ameth.pilltime.features.pilltime.data.datasource.remote.mapper

import com.ameth.pilltime.features.pilltime.data.datasource.remote.model.MedicationDto
import com.ameth.pilltime.features.pilltime.domain.entities.Medicine

fun MedicationDto.toDomain(): Medicine {
    return Medicine(
        id = this.id,
        medication_name = this.medication_name,
        rxcui = this.rxcui,
        dosage = this.dosage,
        frequency_hours = this.frequency_hours,
        start_time = this.start_time,
        is_active = this.is_active,
        created_at = this.created_at
    )
}