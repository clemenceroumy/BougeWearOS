package fr.croumy.bouge.presentation.ui.screens.deadCompanion

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.presentation.constants.KeyStore
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeadCompanionViewModel @Inject constructor(
    companionService: CompanionService,
    val dataStore: DataStore<Preferences>
): ViewModel() {
    val deadCompanion = mutableStateOf<Companion?>(null)

    init {
        viewModelScope.launch {
            deadCompanion.value = companionService.getLastestDeadCompanion()
        }
    }

    suspend fun screenSeen() {
        dataStore.edit { preferences ->
            preferences[KeyStore.COMPANION_DEATH_SEEN] = true
        }
    }
}