package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import kotlinx.coroutines.flow.Flow
import java.time.ZonedDateTime
import java.util.UUID

@Dao
interface CompanionDao {
    @Query("SELECT * FROM companions WHERE deathDate IS NULL LIMIT 1")
    fun getCurrentCompanion(): Flow<CompanionEntity?>

    @Query("SELECT * FROM companions WHERE deathDate IS NOT NULL ORDER BY deathDate DESC")
    suspend fun getCompanionHistory(): List<CompanionEntity>

    @Insert
    suspend fun insertCompanion(companionEntity: CompanionEntity)

    @Query("UPDATE companions SET currentBackgroundUid = :backgroundItemId WHERE deathDate IS NULL")
    suspend fun updateCompanionBackground(backgroundItemId: UUID)

    @Query("UPDATE companions SET happiness = :happiness, hungriness = :hungriness, health = :health WHERE deathDate IS NULL")
    suspend fun updateCompanionStats(happiness: Float, hungriness: Float, health: Float)

    @Query("UPDATE companions SET deathDate = :deathDate WHERE deathDate IS NULL")
    suspend fun updateCompanionDeath(deathDate: ZonedDateTime)
}