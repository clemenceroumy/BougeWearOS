package fr.croumy.bouge.presentation.data.mappers

import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.models.companion.Companion
import fr.croumy.bouge.presentation.models.companion.CompanionType
import fr.croumy.bouge.presentation.models.shop.IShopItem

fun CompanionEntity.toCompanion(): Companion {
    return Companion(
        name = this.name,
        type = CompanionType.fromString(this.type),
        birthDate = this.birthDate,
        deathDate = this.deathDate,
        backgroundId = this.currentBackgroundUid,
    )
}