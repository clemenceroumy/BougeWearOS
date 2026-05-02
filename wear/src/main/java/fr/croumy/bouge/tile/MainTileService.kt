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
import kotlinx.coroutines.flow.firstOrNull
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
        var stats: Stats? = null

        val currentCompanion = companionService.myCompanion.firstOrNull()
        if(currentCompanion != null) {
            stats = companionService.getStats().first()
        }

        return TileBuilders.Tile.Builder()
            .setResourcesVersion(RESOURCES_VERSION)
            .setFreshnessIntervalMillis(60 * 10 * 1000)
            .setTileTimeline(
                tile(requestParams, this, currentCompanion, stats)
            )
            .build()
    }
}

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreview(context: Context) = TilePreviewData() {
    TileBuilders.Tile.Builder()
        .setResourcesVersion(RESOURCES_VERSION)
        .setFreshnessIntervalMillis(60 * 10 * 1000)
        .setTileTimeline(
            tile(it, context, companionMock, Stats(happiness = 2f, hungriness = 3.5f, health = 5f))
        )
        .build()
}