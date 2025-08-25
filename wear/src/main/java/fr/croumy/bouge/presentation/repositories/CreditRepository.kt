package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.CreditEntity
import javax.inject.Inject

class CreditRepository @Inject constructor(
    private val database: AppDatabase
) {
    private val creditDao = database.creditsDao()

    fun insertCredit(creditEntity: CreditEntity) {
        creditDao.insertCredit(creditEntity)
    }

    fun getTotalCredits(): Int {
        return creditDao.getTotal()
    }
}