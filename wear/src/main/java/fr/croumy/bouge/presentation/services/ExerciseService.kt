package fr.croumy.bouge.presentation.services

import fr.croumy.bouge.presentation.repositories.WalkRepository
import jakarta.inject.Inject

class ExerciseService @Inject constructor(
    private val walkRepository: WalkRepository
) {
}