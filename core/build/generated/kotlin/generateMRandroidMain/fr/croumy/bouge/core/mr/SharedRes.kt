package fr.croumy.bouge.core.mr

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.ResourcePlatformDetails
import fr.croumy.bouge.core.R
import kotlin.String
import kotlin.collections.List

public actual object SharedRes {
  private val contentHash: String = "af0fce8b01f91b93aae38f2691829f7d"

  public actual object images : ResourceContainer<ImageResource> {
    public actual override val __platformDetails: ResourcePlatformDetails =
        ResourcePlatformDetails()

    public actual val walking_duck: ImageResource = ImageResource(R.drawable.walking_duck)

    public actual val walking_frog: ImageResource = ImageResource(R.drawable.walking_frog)

    public actual val idle_pig: ImageResource = ImageResource(R.drawable.idle_pig)

    public actual val idle_duck: ImageResource = ImageResource(R.drawable.idle_duck)

    public actual val idle_frog: ImageResource = ImageResource(R.drawable.idle_frog)

    public actual val walking_fox: ImageResource = ImageResource(R.drawable.walking_fox)

    public actual val idle_fox: ImageResource = ImageResource(R.drawable.idle_fox)

    public actual val walking_pig: ImageResource = ImageResource(R.drawable.walking_pig)

    public actual override fun values(): List<ImageResource> = listOf(walking_duck, walking_frog,
        idle_pig, idle_duck, idle_frog, walking_fox, idle_fox, walking_pig)
  }
}
