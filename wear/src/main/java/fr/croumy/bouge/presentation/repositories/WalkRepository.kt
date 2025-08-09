package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.WalkEntity
import fr.croumy.bouge.presentation.data.mappers.toWalk
import fr.croumy.bouge.presentation.models.Walk
import javax.inject.Inject

class WalkRepository @Inject constructor(
    private val database: AppDatabase
) {
    private val walkDao = database.walkDao()

    fun insertWalk(walkEntity: WalkEntity) {
        walkDao.insert(walkEntity)
    }

    fun getAllWalks(): List<Walk> {
        return walkDao.getAll().map {
            it.toWalk()
        }
    }
}