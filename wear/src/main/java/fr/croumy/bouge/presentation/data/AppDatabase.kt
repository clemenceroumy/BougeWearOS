package fr.croumy.bouge.presentation.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.croumy.bouge.presentation.data.converters.DateConverter
import fr.croumy.bouge.presentation.data.dao.CompanionDao
import fr.croumy.bouge.presentation.data.dao.CreditDao
import fr.croumy.bouge.presentation.data.dao.WalkDao
import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.data.entities.CreditEntity
import fr.croumy.bouge.presentation.data.entities.WalkEntity

@Database(entities = [WalkEntity::class, CreditEntity::class, CompanionEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walkDao(): WalkDao
    abstract fun creditsDao(): CreditDao
    abstract fun companionDao(): CompanionDao
}