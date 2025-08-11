package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.CreditEntity

@Dao
interface CreditDao {
    @Query("SELECT SUM(value) FROM credits")
    fun getTotal(): Int

    @Insert
    fun insertCredit(credit: CreditEntity)
}