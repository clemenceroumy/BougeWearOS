package fr.croumy.bouge.presentation.ui.screens.pickCompanion

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.companion.CompanionType
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.core.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.ui.components.Button
import fr.croumy.bouge.presentation.ui.components.IconButton
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import fr.croumy.bouge.presentation.ui.screens.pickCompanion.components.RenameCompanion
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickCompanionScreen(
    pickCompanionViewModel: PickCompanionViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val coroutineScope = rememberCoroutineScope()

    val companions = remember { CompanionType.values }
    val selectedCompanion = remember { mutableStateOf(companions.first()) }
    val customName = remember { mutableStateOf(selectedCompanion.value.defaultName) }
    val pagerState =
        rememberPagerState(initialPage = ((Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % companions.size))) {
            Int.MAX_VALUE
        }

    LaunchedEffect(pagerState.currentPage) {
        val index = pagerState.currentPage % companions.size
        selectedCompanion.value = companions[index]
        customName.value = selectedCompanion.value.defaultName
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(BackgroundItem.MountainTree.assetId),
            contentDescription = "",
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = Dimensions.mediumPadding),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                label = stringResource(R.string.companion_pick),
                size = Dimensions.mediumBtnHeight,
                onClick = {
                    coroutineScope.launch {
                        pickCompanionViewModel.selectCompanion(selectedCompanion.value, customName.value)
                        navController.navigate(NavRoutes.Main.route)
                    }
                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = Dimensions.xsmallPadding)
                    .padding(bottom = Dimensions.spriteBottomPadding),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ArrowBtn(R.drawable.arrow_left) {
                    val prevPage = if (pagerState.currentPage == 0) pagerState.pageCount else pagerState.currentPage - 1
                    coroutineScope.launch { pagerState.animateScrollToPage(prevPage) }
                }
                HorizontalPager(
                    modifier = Modifier.weight(1f),
                    state = pagerState
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom,
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            RenameCompanion(customName) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Image(
                                        painterResource(id = R.drawable.icon_rename),
                                        contentDescription = "",
                                        modifier = Modifier.size(Dimensions.xsmallIcon)
                                    )
                                    OutlinedText(
                                        text = customName.value,
                                        style = MaterialTheme.typography.bodyLarge,
                                        maxLine = 1,
                                    )
                                }
                            }
                            OutlinedText(
                                text = stringResource(R.string.companion_ten_car_max),
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                        AnimatedSprite(
                            Modifier.size(Dimensions.largeIcon),
                            selectedCompanion.value.assetIdleId,
                            selectedCompanion.value.assetIdleFrame,
                        )
                    }
                }
                ArrowBtn(R.drawable.arrow_right) {
                    val nextPage = if (pagerState.currentPage == pagerState.pageCount) 0 else pagerState.currentPage + 1
                    coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
                }
            }
        }
    }
}

@Composable
fun ArrowBtn(
    @DrawableRes icon: Int,
    onClick: () -> Unit,
) {
    IconButton(
        icon = icon,
        size = Dimensions.mediumBtnHeight,
        onClick = onClick,
    )
}