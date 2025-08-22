package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CompanionDao {
    @Query("SELECT * FROM companions WHERE deathDate IS NULL LIMIT 1")
    fun getCurrentCompanion(): Flow<CompanionEntity?>

    @Query("SELECT * FROM companions WHERE deathDate IS NOT NULL ORDER BY deathDate DESC")
    fun getCompanionHistory(): List<CompanionEntity>

    @Insert
    fun insertCompanion(companionEntity: CompanionEntity)
}