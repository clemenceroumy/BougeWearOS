package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.CreditEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface CreditDao {
    @Query("SELECT SUM(value) FROM credits")
    fun getTotal(): Flow<Int?> // Can be null if there are entries in the table

    @Insert
    fun insertCredit(credit: CreditEntity)
}