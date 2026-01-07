package fr.croumy.bouge.presentation.data.mappers

import fr.croumy.bouge.core.models.companion.Companion
import fr.croumy.bouge.core.models.companion.CompanionType
import fr.croumy.bouge.presentation.data.entities.CompanionEntity

fun CompanionEntity.toCompanion(): Companion {
    return Companion(
        name = this.name,
        type = CompanionType.fromString(this.type),
        birthDate = this.birthDate,
        deathDate = this.deathDate,
        backgroundId = this.currentBackgroundUid
    )
}