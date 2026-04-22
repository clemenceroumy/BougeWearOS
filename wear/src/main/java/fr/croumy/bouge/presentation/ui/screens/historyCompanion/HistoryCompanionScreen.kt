package fr.croumy.bouge.presentation.ui.screens.historyCompanion

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.foundation.lazy.ScalingLazyColumn
import androidx.wear.compose.foundation.lazy.ScalingLazyListAnchorType
import androidx.wear.compose.foundation.lazy.items
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.extensions.fillMaxRectangleWidth
import fr.croumy.bouge.presentation.extensions.toYYYYMMDD
import fr.croumy.bouge.presentation.ui.components.NinePatchImage
import fr.croumy.bouge.presentation.ui.components.OutlinedText

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

        if(viewModel.isLoading.value) {
            Column(
                modifier = Modifier.fillMaxRectangle(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedSprite(
                    modifier = Modifier.size(Dimensions.smallIcon),
                    image = ImageBitmap.imageResource(R.drawable.loader),
                    frameCount = 16
                )
            }
        } else {
            if (viewModel.companions.value.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxRectangle(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painterResource(R.drawable.icon_death),
                        contentDescription = null,
                        modifier = Modifier.size(Dimensions.smallIcon)
                    )
                    Spacer(Modifier.height(Dimensions.smallPadding))
                    OutlinedText(
                        text = stringResource(R.string.companions_dead_empty),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                Column(
                    Modifier
                        .fillMaxSize()
                        .padding(top = Dimensions.smallPadding),
                ) {
                    Box(
                        modifier = Modifier.fillMaxRectangleWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        OutlinedText(
                            text = stringResource(R.string.companions_dead_total, viewModel.companions.value.size),
                            style = MaterialTheme.typography.labelSmall,
                            textAlign = TextAlign.Center
                        )
                    }

                    ScalingLazyColumn(
                        contentPadding = PaddingValues(
                            vertical = Dimensions.smallPadding,
                            horizontal = Dimensions.mediumPadding
                        ),
                        anchorType = ScalingLazyListAnchorType.ItemStart
                    ) {
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
                                        Box() {
                                            AnimatedSprite(
                                                modifier = Modifier
                                                    .size(Dimensions.smallIcon)
                                                    .align(Alignment.BottomCenter),
                                                imageId = companion.type.assetIdleId,
                                                frameCount = companion.type.assetIdleFrame,
                                            )
                                            Image(
                                                painterResource(R.drawable.halo),
                                                contentDescription = stringResource(R.string.description_halo),
                                                modifier = Modifier
                                                    .size(Dimensions.xxsmallIcon)
                                                    .offset(y = -Dimensions.xxsmallPadding)
                                                    .align(Alignment.TopCenter)
                                            )
                                        }
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
        }
    }
}