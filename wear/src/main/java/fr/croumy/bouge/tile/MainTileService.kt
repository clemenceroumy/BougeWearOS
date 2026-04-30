package fr.croumy.bouge.tile

import android.content.Context
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.layout.column
import androidx.wear.protolayout.material3.materialScope
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import androidx.wear.tiles.tooling.preview.Preview
import androidx.wear.tiles.tooling.preview.TilePreviewData
import androidx.wear.tooling.preview.devices.WearDevices
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_sky_day
import com.google.android.horologist.annotations.ExperimentalHorologistApi
import com.google.android.horologist.tiles.SuspendingTileService
import dagger.hilt.android.AndroidEntryPoint
import fr.croumy.bouge.core.mocks.companionMock
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.utils.resources.toByteArray
import fr.croumy.bouge.presentation.models.companion.Stats
import fr.croumy.bouge.presentation.services.CompanionService
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private const val RESOURCES_VERSION = "0"

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

private suspend fun resources(
    requestParams: RequestBuilders.ResourcesRequest
): ResourceBuilders.Resources {
    return ResourceBuilders.Resources.Builder()
        .setVersion(RESOURCES_VERSION)
        .addIdToImageMapping(
            "background_sky_day",
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.background_sky_day.toByteArray())
                        .setWidthPx(560)
                        .setHeightPx(560)
                        .build()
                ).build()
        )
        .build()
}

private fun tile(
    requestParams: RequestBuilders.TileRequest,
    context: Context,
    companion: Companion?,
    stats: Stats,
): TileBuilders.Tile {
    val singleTileTimeline = TimelineBuilders.Timeline.Builder()
        .addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder()
                .setLayout(
                    LayoutElementBuilders.Layout.Builder()
                        .setRoot(
                            materialScope(
                                context = context,
                                deviceConfiguration = requestParams.deviceConfiguration,
                            ) {
                                LayoutElementBuilders.Box.Builder()
                                    .setWidth(DimensionBuilders.expand())
                                    .setHeight(DimensionBuilders.expand())
                                    .addContent(
                                        LayoutElementBuilders.Image.Builder()
                                            .setResourceId("background_sky_day")
                                            .setWidth(DimensionBuilders.expand())
                                            .setHeight(DimensionBuilders.expand())
                                            .build()
                                    )
                                    .addContent(
                                        column(
                                            contents = arrayOf(
                                                text(text = "Happiness: ${stats.happiness}".layoutString),
                                                text(text = "Hungriness: ${stats.hungriness}".layoutString),
                                                text(text = "Health: ${stats.health}".layoutString)
                                            ),
                                            horizontalAlignment = LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER,
                                        )
                                    )
                                    .build()
                            })
                        .build()
                )
                .build()
        )
        .build()

    return TileBuilders.Tile.Builder()
        .setResourcesVersion(RESOURCES_VERSION)
        .setFreshnessIntervalMillis(60 * 10 * 1000)
        .setTileTimeline(singleTileTimeline)
        .build()
}

@Preview(device = WearDevices.SMALL_ROUND)
@Preview(device = WearDevices.LARGE_ROUND)
fun tilePreview(context: Context) = TilePreviewData() {
    tile(it, context, companionMock, Stats(happiness = 2f, hungriness = 3.5f, health = 5f))
}