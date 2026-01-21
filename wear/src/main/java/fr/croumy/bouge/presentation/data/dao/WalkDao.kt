package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.WalkEntity
import java.util.UUID

@Dao
interface WalkDao {
    @Query("SELECT * FROM walks")
    suspend fun getAll(): List<WalkEntity>

    @Insert
    suspend fun insert(walk: WalkEntity)

    @Delete
    suspend fun delete(walk: WalkEntity)
}
