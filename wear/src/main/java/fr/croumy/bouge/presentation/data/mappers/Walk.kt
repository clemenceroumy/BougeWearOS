package fr.croumy.bouge.presentation.data.mappers

import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.models.Walk

fun WalkEntity.toWalk(): Walk {
    return Walk(
        steps = this.steps,
        startDateTime = this.start,
        endDateTime = this.end,
    )
}