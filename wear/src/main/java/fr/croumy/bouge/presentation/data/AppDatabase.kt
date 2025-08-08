package fr.croumy.bouge.presentation.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.croumy.bouge.presentation.data.converters.DateConverter
import fr.croumy.bouge.presentation.data.dao.WalkDao
import fr.croumy.bouge.presentation.data.entities.WalkEntity

@Database(entities = [WalkEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walkDao(): WalkDao
}