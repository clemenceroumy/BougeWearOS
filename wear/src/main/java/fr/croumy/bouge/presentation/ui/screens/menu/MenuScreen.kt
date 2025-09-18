package fr.croumy.bouge.presentation.ui.screens.menu

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Fastfood
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
            Row(Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = { navController.navigate(NavRoutes.Feed.route) },
                    label = "feed",
                    icon = Icons.Rounded.Fastfood
                )

                Spacer(Modifier.weight(1f))
            }
        }

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