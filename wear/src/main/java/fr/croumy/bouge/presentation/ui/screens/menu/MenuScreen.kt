package fr.croumy.bouge.presentation.ui.screens.menu

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.Button

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun MenuScreen() {
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

        item {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = { navController.navigate(NavRoutes.Shop.route) },
                label = "shop"
            )
        }
    }
}