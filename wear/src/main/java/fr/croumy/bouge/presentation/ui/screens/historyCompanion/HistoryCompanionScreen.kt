package fr.croumy.bouge.presentation.ui.screens.historyCompanion

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.items
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.extensions.asString
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.extensions.toYYYYMMDD
import fr.croumy.bouge.presentation.ui.components.NinePatchImage

@Composable
fun HistoryCompanionScreen(
    viewModel: HistoryCompanionViewModel = hiltViewModel()
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.background_space),
            contentDescription = "",
        )

        ScalingLazyColumn {
            items(viewModel.companions.value) { companion ->
                Box(
                    Modifier.fillMaxWidth()
                ) {
                    NinePatchImage(
                        modifier = Modifier.matchParentSize(),
                        resId = R.drawable.cloud_btn,
                    )

                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(Dimensions.smallPadding),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.padding(end = Dimensions.smallIcon / 2 + Dimensions.smallPadding)
                        ) {
                            Image(
                                modifier = Modifier
                                    .size(Dimensions.smallIcon)
                                    .offset(x = Dimensions.smallIcon / 2),
                                painter = painterResource(id = R.drawable.grave),
                                contentDescription = ""
                            )
                            AnimatedSprite(
                                modifier = Modifier
                                    .size(Dimensions.smallIcon),
                                imageId = companion.type.assetIdleId,
                                frameCount = companion.type.assetIdleFrame,
                            )
                        }
                        Column {
                            Text(companion.name, style = MaterialTheme.typography.bodyLarge)
                            Text(
                                buildAnnotatedString {
                                    withStyle(style = MaterialTheme.typography.displaySmall.toSpanStyle()) {
                                        append(companion.age.toString())
                                    }
                                    withStyle(style = MaterialTheme.typography.bodySmall.toSpanStyle()) {
                                        append(stringResource(R.string.companion_days_old))
                                    }
                                }
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painterResource(R.drawable.icon_birth),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(Dimensions.xxxsmallIcon)
                                )
                                Text(companion.birthDate.toYYYYMMDD(), style = MaterialTheme.typography.labelMedium)
                                Spacer(Modifier.width(Dimensions.xxsmallPadding))
                                Image(
                                    painterResource(R.drawable.icon_death),
                                    contentDescription = "",
                                    modifier = Modifier.size(Dimensions.xxxsmallIcon)
                                )
                                Text(companion.deathDate!!.toYYYYMMDD(), style = MaterialTheme.typography.labelMedium)
                            }
                        }
                    }
                }
            }
        }
    }
}