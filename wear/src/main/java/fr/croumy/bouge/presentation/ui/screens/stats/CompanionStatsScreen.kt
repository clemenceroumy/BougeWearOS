package fr.croumy.bouge.presentation.ui.screens.stats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_sky_day
import com.google.android.horologist.compose.layout.fillMaxRectangle
import fr.croumy.bouge.R
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.core.theme.Dimensions
import fr.croumy.bouge.presentation.ui.components.IconStatProgressBar
import fr.croumy.bouge.presentation.ui.components.OutlinedText
import org.jetbrains.compose.resources.painterResource

@Composable
fun CompanionStatsScreen(
    viewModel: CompanionStatsViewModel = hiltViewModel(),
    companion: Companion
) {
    val stats = viewModel.stats.collectAsState()

    Box(
        Modifier.fillMaxSize()
    ) {
        Image(
            painterResource(Res.drawable.background_sky_day),
            contentDescription = stringResource(R.string.description_cloudy_background),
        )

        Column(
            Modifier.fillMaxRectangle(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (stats.value == null) CircularProgressIndicator()
            else Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedText(
                    text = companion.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                OutlinedText(
                    text = stringResource(R.string.companion_age, companion.age)
                )
                Spacer(modifier = Modifier.height(Dimensions.smallPadding))

                Column() {
                    IconStatProgressBar(
                        progress = stats.value!!.happiness,
                        stat = StatsType.HAPPINESS
                    )
                    IconStatProgressBar(
                        progress = stats.value!!.hungriness,
                        stat = StatsType.HUNGRINESS
                    )
                    IconStatProgressBar(
                        progress = stats.value!!.health,
                        stat = StatsType.HEALTH
                    )
                }
            }
        }
    }
}