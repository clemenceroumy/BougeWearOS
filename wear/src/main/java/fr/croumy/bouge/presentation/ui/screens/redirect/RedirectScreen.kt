package fr.croumy.bouge.presentation.ui.screens.redirect

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.Button

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun RedirectScreen() {
    val navController = LocalNavController.current

    ScalingLazyColumn(
        Modifier
            .fillMaxSize()
            .padding(Dimensions.mediumPadding)
    ) {
        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(NavRoutes.ExercisesHistory.route) },
                label = "walks"
            )
        }
    }
}