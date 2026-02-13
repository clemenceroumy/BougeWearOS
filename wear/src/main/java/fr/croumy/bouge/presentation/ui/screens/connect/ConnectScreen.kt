package fr.croumy.bouge.presentation.ui.screens.connect

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.navigation.navigateAndPopUpTo
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.Button
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun ConnectScreen(
    viewModel: ConnectViewModel
) {
    val navController = LocalNavController.current

    val companion = viewModel.companion.collectAsState()
    val isAdvertising = viewModel.isAdvertising.collectAsState()
    val isConnected = viewModel.isConnected.collectAsState()
    val isSent = viewModel.isSent.collectAsState()

    val hasBeenSent = remember { mutableStateOf(false) }

    LaunchedEffect(isConnected.value) {
        if (isConnected.value) {
            hasBeenSent.value = true

            // ON CONNECTED, REMOVE BACKSTACK TO KEEP ONLY CONNECT SCREEN
            navController.navigateAndPopUpTo(NavRoutes.Connect.route, NavRoutes.Start.route)
        }

        if (!isConnected.value && hasBeenSent.value) {
            // GET NAVIGATION HISTORY BACK ON RETRIEVING COMPANION
            navController.navigate(NavRoutes.Start.route) {
                launchSingleTop = true
            }
            viewModel.resumeCompanionStatsWorker()
        }
    }

    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(companion.value?.background?.assetId ?: BackgroundItem.MountainTree.assetId),
            contentDescription = "",
        )

        Column(
            Modifier
                .fillMaxSize()
                .padding(bottom = Dimensions.spriteBottomPadding),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (companion.value != null) {
                when {
                    !isAdvertising.value && !isConnected.value && !isSent.value -> Button(
                        size = Dimensions.mediumBtnHeight,
                        label = stringResource(R.string.connect_connect),
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                viewModel.connectToServer()
                            }
                        },
                    )
                    isAdvertising.value && !isConnected.value && !isSent.value -> OutlinedText(text = stringResource(R.string.connect_connecting))
                    !isAdvertising.value && isConnected.value && !isSent.value -> OutlinedText(text = stringResource(R.string.connect_sending, companion.value!!.name))
                }

                if (!isSent.value) {
                    AnimatedSprite(
                        modifier = Modifier.size(Dimensions.largeIcon),
                        imageId = companion.value!!.type.assetIdleId,
                        frameCount = companion.value!!.type.assetIdleFrame
                    )
                } else if(!isAdvertising.value && isConnected.value && isSent.value) {
                    Box(
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Image(
                            painter = painterResource(R.drawable.wood_sign),
                            contentDescription = stringResource(R.string.description_away_wood_sign),
                        )
                        Text(
                            stringResource(R.string.connect_away),
                            modifier = Modifier.padding(top = Dimensions.xsmallPadding),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            } else {
                CircularProgressIndicator()
            }
        }
    }
}
