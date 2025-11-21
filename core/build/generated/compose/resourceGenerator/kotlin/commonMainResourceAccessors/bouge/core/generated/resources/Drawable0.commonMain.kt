@file:OptIn(InternalResourceApi::class)

package bouge.core.generated.resources

import kotlin.OptIn
import kotlin.String
import kotlin.collections.MutableMap
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.InternalResourceApi
import org.jetbrains.compose.resources.ResourceItem

private const val MD: String = "composeResources/bouge.core.generated.resources/"

internal val Res.drawable.idle_duck: DrawableResource by lazy {
      DrawableResource("drawable:idle_duck", setOf(
        ResourceItem(setOf(), "${MD}drawable/idle_duck.png", -1, -1),
      ))
    }

internal val Res.drawable.idle_fox: DrawableResource by lazy {
      DrawableResource("drawable:idle_fox", setOf(
        ResourceItem(setOf(), "${MD}drawable/idle_fox.png", -1, -1),
      ))
    }

internal val Res.drawable.idle_frog: DrawableResource by lazy {
      DrawableResource("drawable:idle_frog", setOf(
        ResourceItem(setOf(), "${MD}drawable/idle_frog.png", -1, -1),
      ))
    }

internal val Res.drawable.idle_pig: DrawableResource by lazy {
      DrawableResource("drawable:idle_pig", setOf(
        ResourceItem(setOf(), "${MD}drawable/idle_pig.png", -1, -1),
      ))
    }

internal val Res.drawable.walking_duck: DrawableResource by lazy {
      DrawableResource("drawable:walking_duck", setOf(
        ResourceItem(setOf(), "${MD}drawable/walking_duck.png", -1, -1),
      ))
    }

internal val Res.drawable.walking_fox: DrawableResource by lazy {
      DrawableResource("drawable:walking_fox", setOf(
        ResourceItem(setOf(), "${MD}drawable/walking_fox.png", -1, -1),
      ))
    }

internal val Res.drawable.walking_frog: DrawableResource by lazy {
      DrawableResource("drawable:walking_frog", setOf(
        ResourceItem(setOf(), "${MD}drawable/walking_frog.png", -1, -1),
      ))
    }

internal val Res.drawable.walking_pig: DrawableResource by lazy {
      DrawableResource("drawable:walking_pig", setOf(
        ResourceItem(setOf(), "${MD}drawable/walking_pig.png", -1, -1),
      ))
    }

@InternalResourceApi
internal fun _collectCommonMainDrawable0Resources(map: MutableMap<String, DrawableResource>) {
  map.put("idle_duck", Res.drawable.idle_duck)
  map.put("idle_fox", Res.drawable.idle_fox)
  map.put("idle_frog", Res.drawable.idle_frog)
  map.put("idle_pig", Res.drawable.idle_pig)
  map.put("walking_duck", Res.drawable.walking_duck)
  map.put("walking_fox", Res.drawable.walking_fox)
  map.put("walking_frog", Res.drawable.walking_frog)
  map.put("walking_pig", Res.drawable.walking_pig)
}
