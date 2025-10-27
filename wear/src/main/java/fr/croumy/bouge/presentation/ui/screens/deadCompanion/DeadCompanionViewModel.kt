package fr.croumy.bouge.presentation.ui.screens.deadCompanion

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.croumy.bouge.presentation.constants.KeyStore
import fr.croumy.bouge.presentation.services.CompanionService
import javax.inject.Inject

@HiltViewModel
class DeadCompanionViewModel @Inject constructor(
    companionService: CompanionService,
    val dataStore: DataStore<Preferences>
): ViewModel() {
    val deadCompanion = companionService.getLastestDeadCompanion()

    suspend fun screenSeen() {
        dataStore.edit { preferences ->
            preferences[KeyStore.COMPANION_DEATH_SEEN] = true
        }
    }
}