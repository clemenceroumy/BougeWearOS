package fr.croumy.bouge.core.models.companion

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.idle_duck
import bouge.core.generated.resources.idle_fox
import bouge.core.generated.resources.idle_frog
import bouge.core.generated.resources.idle_pig
import bouge.core.generated.resources.walking_duck
import bouge.core.generated.resources.walking_fox
import bouge.core.generated.resources.walking_frog
import bouge.core.generated.resources.walking_pig
import fr.croumy.bouge.core.utils.serializer.KDrawableResourceSerializer
import fr.croumy.bouge.core.utils.serializer.KZonedDateTimeSerializer
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

@Serializable
sealed class CompanionType(
    @Serializable(KDrawableResourceSerializer::class) val assetIdleId: DrawableResource,
    val assetIdleFrame: Int,
    @Serializable(KDrawableResourceSerializer::class) val assetWalkingId: DrawableResource,
    val assetWalkingFrame: Int,
    val defaultName: String
) {
    @Serializable object Frog : CompanionType(Res.drawable.idle_frog, 4, Res.drawable.walking_frog, 7, "Froggy")
    @Serializable object Pig : CompanionType(Res.drawable.idle_pig, 5, Res.drawable.walking_pig, 4, "Piggy")
    @Serializable object Fox : CompanionType(Res.drawable.idle_fox, 2, Res.drawable.walking_fox, 4, "Foxy")
    @Serializable object Duck : CompanionType(Res.drawable.idle_duck, 2, Res.drawable.walking_duck, 6, "Ducky")

    companion object {
        val values = listOf(Frog, Pig, Fox, Duck)
        fun fromString(name: String): CompanionType {
            return values.find { it.javaClass.simpleName == name }!!
        }
    }
}