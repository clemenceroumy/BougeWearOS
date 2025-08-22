package fr.croumy.bouge.presentation.ui.screens

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.HealthService
import javax.inject.Inject

@HiltViewModel
class StartViewModel @Inject constructor(
    private val healthService: HealthService,
    private val companionService: CompanionService
): ViewModel() {
    val hasCompanion get() = companionService.myCompanion.value != null

    fun initHealthService() {
        healthService.initService()
    }
}