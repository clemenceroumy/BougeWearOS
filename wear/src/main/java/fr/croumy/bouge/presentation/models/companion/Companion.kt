package fr.croumy.bouge.presentation.models.companion

import fr.croumy.bouge.presentation.models.shop.IShopItem
import java.time.Duration
import java.time.ZonedDateTime
import java.util.UUID

data class Companion(
    val name: String,
    val type: CompanionType,
    val birthDate: ZonedDateTime,
    val backgroundId: UUID?,
) {
    val age: Int = Duration.between(birthDate, ZonedDateTime.now()).toDays().toInt()
}