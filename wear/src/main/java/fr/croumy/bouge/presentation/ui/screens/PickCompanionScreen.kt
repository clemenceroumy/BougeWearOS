package fr.croumy.bouge.presentation.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowLeft
import androidx.compose.material.icons.automirrored.filled.ArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import fr.croumy.bouge.presentation.injection.LocalNavController
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.navigation.NavRoutes
import fr.croumy.bouge.presentation.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.AnimatedSprite
import fr.croumy.bouge.presentation.ui.components.Button
import fr.croumy.bouge.presentation.ui.components.IconButton
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PickCompanionScreen(
    pickCompanionViewModel: PickCompanionViewModel = hiltViewModel()
) {
    val navController = LocalNavController.current
    val coroutineScope = rememberCoroutineScope()

    val companions = remember { CompanionType.values }
    val selectedCompanion = remember { mutableStateOf(companions.first()) }
    val pagerState =
        rememberPagerState(initialPage = ((Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % companions.size))) {
            Int.MAX_VALUE
        }

    LaunchedEffect(pagerState.currentPage) {
        val index = pagerState.currentPage % companions.size
        selectedCompanion.value = companions[index]
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = Dimensions.smallPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Dimensions.xsmallPadding)
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ArrowBtn(Icons.AutoMirrored.Filled.ArrowLeft) {
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
                    verticalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(selectedCompanion.value.defaultName)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        AnimatedSprite(
                            selectedCompanion.value.assetIdleId,
                            selectedCompanion.value.assetIdleFrame,
                        )
                    }
                }
            }
            ArrowBtn(Icons.AutoMirrored.Filled.ArrowRight) {
                val nextPage = if (pagerState.currentPage == pagerState.pageCount) 0 else pagerState.currentPage + 1
                coroutineScope.launch { pagerState.animateScrollToPage(nextPage) }
            }
        }
        IconButton(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            icon = Icons.Default.Check,
            onClick = {
                pickCompanionViewModel.selectCompanion(selectedCompanion.value)
                navController.navigate(NavRoutes.Home.route)
            },
        )
    }
}

@Composable
fun ArrowBtn(
    icon: ImageVector,
    onClick: () -> Unit,
) {
    IconButton(
        modifier = Modifier.offset(y = Dimensions.btnHeight / 2),
        icon = icon,
        size = Dimensions.smallBtnHeight,
        containerColor = MaterialTheme.colorScheme.surface,
        contentColor = MaterialTheme.colorScheme.onSurface,
        onClick = onClick,
    )
}