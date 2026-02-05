package fr.croumy.bouge.core.mocks

import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.shop.background.BackgroundItem
import java.time.ZonedDateTime

@Suppress("NewApi")
val companionMock = Companion(
    name = "Buddy",
    type = fr.croumy.bouge.core.models.companion.CompanionType.Pig,
    birthDate = ZonedDateTime.parse("2025-11-28T10:00:00Z"),
    deathDate = null,
    backgroundId = BackgroundItem.MountainTree.id
)