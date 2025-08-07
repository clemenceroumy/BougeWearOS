package fr.croumy.bouge.presentation.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import fr.croumy.bouge.presentation.data.entities.WalkEntity

@Dao
interface WalkDao {
    @Query("SELECT * FROM walks")
    fun getAll(): List<WalkEntity>

    @Insert
    fun insert(vararg walk: WalkEntity)

    @Delete
    fun delete(walk: WalkEntity)
}
