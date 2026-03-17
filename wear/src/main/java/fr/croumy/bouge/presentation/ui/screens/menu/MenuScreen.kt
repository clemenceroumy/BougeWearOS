package fr.croumy.bouge.presentation.ui.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.CloudButton

@OptIn(ExperimentalHorologistApi::class)
@Composable
fun MenuScreen() {
    val navController = LocalNavController.current

    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(R.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        ScalingLazyColumn(
            Modifier
                .fillMaxSize()
                .padding(Dimensions.mediumPadding)
        ) {
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Dimensions.mediumPadding)
                ) {
                    CloudButton(
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(NavRoutes.Feed.route) },
                        icon = R.drawable.icon_feed
                    )

                    Spacer(Modifier.width(Dimensions.smallPadding))

                    CloudButton(
                        modifier = Modifier.weight(1f),
                        onClick = { navController.navigate(NavRoutes.Play.route) },
                        icon = R.drawable.icon_play
                    )
                }
            }

            item {
                CloudButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(NavRoutes.Shop.route) },
                    label = stringResource(R.string.menu_shop),
                    icon = R.drawable.icon_shop
                )
            }

            item {
                CloudButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(NavRoutes.Connect.route) },
                    label = stringResource(R.string.menu_connect),
                    icon = R.drawable.icon_connect
                )
            }

            item {
                CloudButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(NavRoutes.ExercisesHistory.route) },
                    label = stringResource(R.string.menu_walks),
                    icon = R.drawable.icon_walk
                )
            }

            item {
                CloudButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(NavRoutes.Background.route) },
                    label = stringResource(R.string.menu_backgrounds),
                    icon = R.drawable.icon_backgrounds
                )
            }

            item {
                CloudButton(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { navController.navigate(NavRoutes.CompanionHistory.route) },
                    label = stringResource(R.string.menu_deads),
                    icon = R.drawable.icon_death
                )
            }
        }
    }
}