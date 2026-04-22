package fr.croumy.bouge.core.mocks

import androidx.annotation.RequiresApi
import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.companion.CompanionType
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import java.time.ZonedDateTime

@Suppress("NewApi")
val companionMock = Companion(
    name = "Buddy",
    type = CompanionType.Pig,
    birthDate = ZonedDateTime.parse("2025-11-28T10:00:00Z"),
    deathDate = null,
    backgroundId = BackgroundItem.MountainTree.id
)

@Suppress("NewApi")
val companionPlaceholder = Companion(
    name = "Placeholder",
    type = CompanionType.Placeholder,
    birthDate = ZonedDateTime.parse("2025-11-28T10:00:00Z"),
    deathDate = null,
    backgroundId = BackgroundItem.MountainTree.id
)