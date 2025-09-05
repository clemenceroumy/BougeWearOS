package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.DailyStepsEntity
import fr.croumy.bouge.presentation.data.entities.WalkEntity

@Dao
interface DailyStepsDao {
    @Query("SELECT * FROM dailySteps")
    fun getAll(): List<DailyStepsEntity>

    @Query("SELECT * FROM dailySteps WHERE date = :date")
    fun getByDate(date: String): DailyStepsEntity?

    @Insert(onConflict = REPLACE)
    fun insert(steps: DailyStepsEntity)
}
