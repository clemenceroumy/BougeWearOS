package fr.croumy.bouge.core.utils.serializer

import bouge.core.generated.resources.Res
import bouge.core.generated.resources.idle_duck
import bouge.core.generated.resources.idle_fox
import bouge.core.generated.resources.idle_frog
import bouge.core.generated.resources.idle_pig
import bouge.core.generated.resources.walking_duck
import bouge.core.generated.resources.walking_fox
import bouge.core.generated.resources.walking_frog
import bouge.core.generated.resources.walking_pig
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeCollection
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.DrawableResource


object KDrawableResourceSerializer: KSerializer<DrawableResource> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("DrawableResource", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: DrawableResource) {
        val resourceName = when (value) {
            Res.drawable.idle_frog -> "idle_frog"
            Res.drawable.idle_pig -> "idle_pig"
            Res.drawable.idle_fox -> "idle_fox"
            Res.drawable.idle_duck -> "idle_duck"
            Res.drawable.walking_frog -> "walking_frog"
            Res.drawable.walking_pig -> "walking_pig"
            Res.drawable.walking_fox -> "walking_fox"
            Res.drawable.walking_duck -> "walking_duck"
            else -> throw IllegalArgumentException("Unknown DrawableResource")
        }
        encoder.encodeString(resourceName)
    }

    override fun deserialize(decoder: Decoder): DrawableResource {
        val resourceName = decoder.decodeString()
        return when (resourceName) {
            "idle_frog" -> Res.drawable.idle_frog
            "idle_pig" -> Res.drawable.idle_pig
            "idle_fox" -> Res.drawable.idle_fox
            "idle_duck" -> Res.drawable.idle_duck
            "walking_frog" -> Res.drawable.walking_frog
            "walking_pig" -> Res.drawable.walking_pig
            "walking_fox" -> Res.drawable.walking_fox
            "walking_duck" -> Res.drawable.walking_duck
            else -> throw IllegalArgumentException("Unknown DrawableResource name: $resourceName")
        }
    }
}