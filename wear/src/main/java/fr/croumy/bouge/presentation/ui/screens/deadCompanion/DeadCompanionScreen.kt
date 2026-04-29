package fr.croumy.bouge.presentation.ui.screens.deadCompanion

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.ui.components.Button
import fr.croumy.bouge.presentation.ui.components.OutlinedText

@Composable
fun DeadCompanionScreen(
    viewModel: DeadCompanionViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val opacity = remember { mutableFloatStateOf(1f) }
    val goodbyeAnimation = animateFloatAsState(opacity.floatValue)
    val infiniteAnimation = rememberInfiniteTransition(label = "infinite")
    val haloAnimation = infiniteAnimation.animateFloat(
        initialValue = 0.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800),
            repeatMode = RepeatMode.Reverse
        )
    )

    LaunchedEffect(goodbyeAnimation.value) {
        if (goodbyeAnimation.value == 0f) {
            viewModel.screenSeen()
            navController.navigate(NavRoutes.PickCompanion.route)
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background_space),
            contentDescription = "",
        )

        viewModel.deadCompanion.value?.let { deadCompanion ->
            Column(
                Modifier.fillMaxRectangle(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                OutlinedText(
                    text = stringResource(R.string.companion_dead_desc, deadCompanion.name),
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center
                )
                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Image(
                        painterResource(R.drawable.halo),
                        contentDescription = stringResource(R.string.description_halo),
                        modifier = Modifier.offset(y = (haloAnimation.value).dp)
                    )
                    AnimatedSprite(
                        modifier = Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .alpha(goodbyeAnimation.value),
                        imageId = deadCompanion.type.assetIdleId,
                        frameCount = deadCompanion.type.assetIdleFrame,
                    )
                }
                Button(
                    onClick = { opacity.floatValue = 0f },
                    label = stringResource(R.string.companion_good_bye),
                    size = Dimensions.iconBtnHeight
                )
            }
        }
    }
}