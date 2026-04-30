package fr.croumy.bouge.tile

import androidx.wear.protolayout.ResourceBuilders
import androidx.wear.tiles.RequestBuilders
import bouge.core.generated.resources.Res
import bouge.core.generated.resources.background_sky_day
import bouge.core.generated.resources.happiness_0
import bouge.core.generated.resources.happiness_0_25
import bouge.core.generated.resources.happiness_0_5
import bouge.core.generated.resources.happiness_0_75
import bouge.core.generated.resources.happiness_1
import bouge.core.generated.resources.health_0
import bouge.core.generated.resources.health_0_25
import bouge.core.generated.resources.health_0_5
import bouge.core.generated.resources.health_0_75
import bouge.core.generated.resources.health_1
import bouge.core.generated.resources.hungriness_0
import bouge.core.generated.resources.hungriness_0_25
import bouge.core.generated.resources.hungriness_0_5
import bouge.core.generated.resources.hungriness_0_75
import bouge.core.generated.resources.hungriness_1
import fr.croumy.bouge.core.utils.resources.toByteArray

const val RESOURCES_VERSION = "0"

suspend fun resources(
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
        .addIdToImageMapping(
            Res.drawable.hungriness_0.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.hungriness_0.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.hungriness_0_25.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.hungriness_0_25.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.hungriness_0_5.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.hungriness_0_5.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.hungriness_0_75.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.hungriness_0_75.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.hungriness_1.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.hungriness_1.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.happiness_0.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.happiness_0.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.happiness_0_25.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.happiness_0_25.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.happiness_0_5.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.happiness_0_5.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.happiness_0_75.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.happiness_0_75.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.happiness_1.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.happiness_1.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.health_0.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.health_0.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.health_0_25.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.health_0_25.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.health_0_5.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.health_0_5.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.health_0_75.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.health_0_75.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .addIdToImageMapping(
            Res.drawable.health_1.toString(),
            ResourceBuilders.ImageResource.Builder()
                .setInlineResource(
                    ResourceBuilders.InlineImageResource.Builder()
                        .setData(Res.drawable.health_1.toByteArray())
                        .setWidthPx(64)
                        .setHeightPx(64)
                        .build()
                ).build()
        )
        .build()
}