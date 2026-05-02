package fr.croumy.bouge.tile

import android.content.Context
import android.util.Log
import androidx.wear.protolayout.ActionBuilders
import androidx.wear.protolayout.DimensionBuilders
import androidx.wear.protolayout.LayoutElementBuilders
import androidx.wear.protolayout.ModifiersBuilders
import androidx.wear.protolayout.TimelineBuilders
import androidx.wear.protolayout.layout.column
import androidx.wear.protolayout.layout.row
import androidx.wear.protolayout.layout.spacer
import androidx.wear.protolayout.material3.ColorScheme
import androidx.wear.protolayout.material3.MaterialScope
import androidx.wear.protolayout.material3.materialScope
import androidx.wear.protolayout.material3.text
import androidx.wear.protolayout.types.layoutString
import androidx.wear.tiles.RequestBuilders
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_sky_day
import fr.croumy.bouge.R

import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.companion.StatsType
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.constants.Constants
import fr.croumy.bouge.presentation.models.companion.Stats

fun tile(
    requestParams: RequestBuilders.TileRequest,
    context: Context,
    companion: Companion?,
    stats: Stats?,
): TimelineBuilders.Timeline {
    val clickable = ModifiersBuilders.Clickable.Builder()
        .setId("open_app")
        .setOnClick(
            ActionBuilders.LaunchAction.Builder()
                .setAndroidActivity(
                    ActionBuilders.AndroidActivity.Builder()
                        .setPackageName(context.packageName)
                        .setClassName(MainActivity::class.java.name)
                        .build()
                ).build()
        ).build()

    return TimelineBuilders.Timeline.Builder()
        .addTimelineEntry(
            TimelineBuilders.TimelineEntry.Builder()
                .setLayout(
                    LayoutElementBuilders.Layout.Builder()
                        .setRoot(
                            materialScope(
                                context = context,
                                deviceConfiguration = requestParams.deviceConfiguration,
                                defaultColorScheme = TileTheme
                            ) {
                                LayoutElementBuilders.Box.Builder()
                                    .setWidth(DimensionBuilders.expand())
                                    .setHeight(DimensionBuilders.expand())
                                    .setModifiers(
                                        ModifiersBuilders.Modifiers.Builder()
                                            .setClickable(clickable)
                                            .build()
                                    )
                                    .addContent(
                                        LayoutElementBuilders.Image.Builder()
                                            .setResourceId(
                                                if (companion == null) R.drawable.background_space.toString()
                                                else Res.drawable.background_sky_day.toString()
                                            )
                                            .setWidth(DimensionBuilders.expand())
                                            .setHeight(DimensionBuilders.expand())
                                            .build()
                                    )
                                    .addContent(
                                        if (companion == null) {
                                            column(
                                                contents = arrayOf(
                                                    text(
                                                        text = context.getString(R.string.companion_dead_notif).layoutString,
                                                        alignment = LayoutElementBuilders.TEXT_ALIGN_CENTER,
                                                        maxLines = 3,
                                                        color = this.colorScheme.primary
                                                    )
                                                )
                                            )
                                        } else {
                                            column(
                                                contents = arrayOf(
                                                    text(
                                                        text = companion.name.layoutString,
                                                        color = this.colorScheme.primary
                                                    ),
                                                    spacer(height = DimensionBuilders.dp(10f)),
                                                    statRow(stats!!.happiness, StatsType.HAPPINESS),
                                                    statRow(stats.hungriness, StatsType.HUNGRINESS),
                                                    statRow(stats.health, StatsType.HEALTH),
                                                ),
                                                horizontalAlignment = LayoutElementBuilders.HORIZONTAL_ALIGN_CENTER
                                            )
                                        }
                                    )
                                    .build()
                            })
                        .build()
                )
                .build()
        )
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