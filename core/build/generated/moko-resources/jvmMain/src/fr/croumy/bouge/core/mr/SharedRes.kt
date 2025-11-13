package fr.croumy.bouge.core.mr

import dev.icerock.moko.graphics.Color
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.ResourceContainer
import dev.icerock.moko.resources.ResourcePlatformDetails
import java.lang.ClassLoader
import kotlin.String
import kotlin.collections.List

public actual object SharedRes {
  private val contentHash: String = "af0fce8b01f91b93aae38f2691829f7d"

  private val resourcesClassLoader: ClassLoader = SharedRes::class.java.classLoader

  public actual object images : ResourceContainer<ImageResource> {
    public actual override val __platformDetails: ResourcePlatformDetails =
        ResourcePlatformDetails(resourcesClassLoader)

    public actual val walking_duck: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/walking_duck.png", darkFilePath =
        null)

    public actual val walking_frog: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/walking_frog.png", darkFilePath =
        null)

    public actual val idle_pig: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/idle_pig.png", darkFilePath =
        null)

    public actual val idle_duck: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/idle_duck.png", darkFilePath =
        null)

    public actual val idle_frog: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/idle_frog.png", darkFilePath =
        null)

    public actual val walking_fox: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/walking_fox.png", darkFilePath =
        null)

    public actual val idle_fox: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/idle_fox.png", darkFilePath =
        null)

    public actual val walking_pig: ImageResource = ImageResource(resourcesClassLoader =
        __platformDetails.resourcesClassLoader, filePath = "images/walking_pig.png", darkFilePath =
        null)

    public actual override fun values(): List<ImageResource> = listOf(walking_duck, walking_frog,
        idle_pig, idle_duck, idle_frog, walking_fox, idle_fox, walking_pig)
  }
}
