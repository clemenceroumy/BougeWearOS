package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.services.CompanionService
import fr.croumy.bouge.presentation.services.HealthService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@HiltViewModel
class StartViewModel @Inject constructor(
    private val healthService: HealthService,
    private val companionService: CompanionService
) : ViewModel() {
    val isLoading = mutableStateOf(true)
    val hasCompanion = mutableStateOf(false)

    init {
        this.viewModelScope.launch {
            hasCompanion.value = companionService.myCompanion
                .transform { emit(it != null) }
                .first()
            isLoading.value = false
        }
    }

    fun initHealthService() {
        healthService.initService()
    }
}