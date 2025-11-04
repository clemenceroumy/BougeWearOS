package fr.croumy.bouge.presentation.ui.screens.deadCompanion

import androidx.compose.animation.core.AnimationState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateTo
import androidx.compose.animation.core.isFinished
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.ui.components.Button
import kotlinx.coroutines.launch

@Composable
fun DeadCompanionScreen(
    viewModel: DeadCompanionViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val coroutine = rememberCoroutineScope()
    val opacity = remember { mutableFloatStateOf(1f) }
    val goodbyeAnimation = animateFloatAsState(opacity.floatValue)

    LaunchedEffect(goodbyeAnimation.value) {
        if(goodbyeAnimation.value == 0f) {
            viewModel.screenSeen()
            navController.navigate(NavRoutes.PickCompanion.route)
        }
    }

    viewModel.deadCompanion?.let{ deadCompanion ->
        Column(
            Modifier.fillMaxRectangle(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                stringResource(R.string.companion_dead_desc),
                style = MaterialTheme.typography.labelMedium
            )
            AnimatedSprite(
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
                    .alpha(goodbyeAnimation.value),
                imageId = deadCompanion.type.assetIdleId,
            frameCount = deadCompanion.type.assetIdleFrame,
            )
            Text(
                deadCompanion.name,
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(Modifier.height(Dimensions.xsmallPadding))
            Button(
                onClick = { opacity.floatValue = 0f },
                label = stringResource(R.string.companion_good_bye),
                size = Dimensions.iconBtnHeight
            )
        }
    }
}