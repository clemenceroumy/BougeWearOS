package fr.croumy.bouge.tile

import android.content.Context
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tooling.preview.devices.WearDevices
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.core.mocks.companionMock
import fr.croumy.bouge.presentation.models.companion.Stats
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalHorologistApi::class)
@AndroidEntryPoint
class MainTileService : SuspendingTileService() {
    @Inject
    lateinit var companionService: CompanionService

    override suspend fun resourcesRequest(
        requestParams: RequestBuilders.ResourcesRequest
    ) = resources(requestParams)

    override suspend fun tileRequest(
        requestParams: RequestBuilders.TileRequest
    ): TileBuilders.Tile {
        val currentCompanion = companionService.myCompanion.first()
        val stats = companionService.getStats().first()

        return tile(requestParams, this, currentCompanion, stats)
    }
}

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreview(context: Context) = TilePreviewData() {
    tile(it, context, companionMock, Stats(happiness = 2f, hungriness = 3.5f, health = 5f))
}