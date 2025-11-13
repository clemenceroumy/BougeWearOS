package fr.croumy.bouge.core.mr

import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.ResourcePlatformDetails
import kotlin.collections.List

public expect object SharedRes {
  public object images : ResourceContainer<ImageResource> {
    public override val __platformDetails: ResourcePlatformDetails

    public val walking_duck: ImageResource

    public val walking_frog: ImageResource

    public val idle_pig: ImageResource

    public val idle_duck: ImageResource

    public val idle_frog: ImageResource

    public val walking_fox: ImageResource

    public val idle_fox: ImageResource

    public val walking_pig: ImageResource

    public override fun values(): List<ImageResource>
  }
}
