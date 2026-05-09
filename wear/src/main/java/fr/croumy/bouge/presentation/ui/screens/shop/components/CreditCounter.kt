package fr.croumy.bouge.presentation.ui.screens.shop.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import fr.croumy.bouge.R
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.OutlinedText

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CreditCounter(
    modifier: Modifier = Modifier,
    credit: Int,
) {

    Row(
        modifier.padding(top = Dimensions.mediumPadding)
    ) {
        Image(
            painter = painterResource(R.drawable.icon_coin),
            contentDescription = null,
            modifier = Modifier.size(Dimensions.xxsmallIcon)
        )
        AnimatedContent(
            targetState = credit,
            transitionSpec = {
                (slideInVertically { fullHeight -> fullHeight } + fadeIn())
                    .togetherWith(slideOutVertically { fullHeight -> -fullHeight } + fadeOut())
            }
        ) { targetCount ->
            OutlinedText(
                text = targetCount.toString()
            )
        }
    }
}