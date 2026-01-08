package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.BleServer
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.HealthService
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@HiltViewModel
class StartViewModel @Inject constructor(
    private val healthService: HealthService,
    private val companionService: CompanionService,
    private val bleServer: BleServer
) : ViewModel() {
    val isLoading = mutableStateOf(true)
    val hasCompanion = mutableStateOf(false)
    val isCompanionAvailable = mutableStateOf(false)

    init {
        this.viewModelScope.launch {
            val companion = companionService.myCompanion.first()
            hasCompanion.value = companion != null
            isCompanionAvailable.value = !bleServer.isConnected.value
            isLoading.value = false
        }
    }

    fun initHealthService() {
        healthService.initService()
    }
}