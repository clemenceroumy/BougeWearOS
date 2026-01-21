package fr.croumy.bouge.presentation.repositories

import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.entities.CreditEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreditRepository @Inject constructor(
    private val database: AppDatabase
) {
    private val creditDao = database.creditsDao()

    suspend fun insertCredit(creditEntity: CreditEntity) {
        return creditDao.insertCredit(creditEntity)
    }

    fun getTotalCredits(): Flow<Int?> {
        return creditDao.getTotal()
    }
}