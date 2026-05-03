package fr.croumy.bouge.core.utils.resources

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.getDrawableResourceBytes
import org.jetbrains.compose.resources.getSystemResourceEnvironment

suspend fun DrawableResource.toByteArray(): ByteArray {
    return getDrawableResourceBytes(
        environment = getSystemResourceEnvironment(),
        resource = this
    )
}