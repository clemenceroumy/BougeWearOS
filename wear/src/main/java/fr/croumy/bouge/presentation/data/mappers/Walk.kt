package fr.croumy.bouge.presentation.data.mappers

import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.models.Walk

fun WalkEntity.toWalk(): Walk {
    return Walk(
        steps = this.steps,
        startTime = this.start,
        endTime = this.end,
    )
}