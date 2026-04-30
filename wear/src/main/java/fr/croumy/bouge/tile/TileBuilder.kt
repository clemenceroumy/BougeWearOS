package fr.croumy.bouge.tile

import android.content.Context
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.layout.column
import androidx.wear.protolayout.layout.row
import androidx.wear.protolayout.layout.spacer
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.materialScope
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.RequestBuilders
import androidx.wear.tiles.TileBuilders
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.presentation.models.companion.Stats

fun tile(
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
                                                text(text = companion?.name?.layoutString ?: "companion".layoutString),
                                                spacer(height = DimensionBuilders.dp(10f)),
                                                statRow(stats.happiness, StatsType.HAPPINESS),
                                                statRow(stats.hungriness, StatsType.HUNGRINESS),
                                                statRow(stats.health, StatsType.HEALTH),
                                            ),
                                            horizontalAlignment = LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
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

private fun MaterialScope.statRow(
    progress: Float,
    stat: StatsType
): LayoutElementBuilders.Row {
    val full = progress.toInt()
    val partial = progress - full
    val empty = (Constants.STAT_MAX - progress).toInt()

    return row(
        verticalAlignment = LayoutElementBuilders.VERTICAL_ALIGN_CENTER,
        contents = arrayOf(
            row(
                width = DimensionBuilders.wrap(),
                contents = List(full) { stat.assetFromProgress(1f) }.map {
                    LayoutElementBuilders.Image.Builder()
                        .setResourceId(it.toString())
                        .setWidth(DimensionBuilders.dp(25f))
                        .setHeight(DimensionBuilders.dp(25f))
                        .build()
                }.toTypedArray(),
            ),
            row(
                width = DimensionBuilders.wrap(),
                contents = if (partial > 0f) arrayOf(
                    LayoutElementBuilders.Image.Builder()
                        .setResourceId(stat.assetFromProgress(partial).toString())
                        .setWidth(DimensionBuilders.dp(25f))
                        .setHeight(DimensionBuilders.dp(25f))
                        .build()
                ) else emptyArray()
            ),
            row(
                width = DimensionBuilders.wrap(),
                contents = List(empty) { stat.assetFromProgress(0f) }.map {
                    LayoutElementBuilders.Image.Builder()
                        .setResourceId(it.toString())
                        .setWidth(DimensionBuilders.dp(25f))
                        .setHeight(DimensionBuilders.dp(25f))
                        .build()
                }.toTypedArray()
            ),
        ),
    )
}