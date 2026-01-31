package com.ameth.pilltime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ameth.pilltime.core.di.AppContainer
import com.ameth.pilltime.features.pilltime.di.MedicineModule
import com.ameth.pilltime.features.pilltime.presentation.screens.MedicineScreen
import com.example.compose.AppTheme

class MainActivity : ComponentActivity() {
    lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)
        val medicineModule = MedicineModule(appContainer)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MedicineScreen(medicineModule.provideMedicineViewModelFactory())
            }
        }
    }
}