package fr.croumy.bouge.presentation.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.croumy.bouge.presentation.data.converters.DateConverter
import fr.croumy.bouge.presentation.data.converters.UUIDConverter
import fr.croumy.bouge.presentation.data.dao.CompanionDao
import fr.croumy.bouge.presentation.data.dao.CreditDao
import fr.croumy.bouge.presentation.data.dao.DailyStepsDao
import fr.croumy.bouge.presentation.data.dao.InventoryDao
import fr.croumy.bouge.presentation.data.dao.WalkDao
import fr.croumy.bouge.presentation.data.entities.CompanionEntity
import fr.croumy.bouge.presentation.data.entities.CreditEntity
import fr.croumy.bouge.presentation.data.entities.DailyStepsEntity
import fr.croumy.bouge.presentation.data.entities.InventoryEntity
import fr.croumy.bouge.presentation.data.entities.WalkEntity

@Database(entities = [WalkEntity::class, CreditEntity::class, InventoryEntity::class, CompanionEntity::class, DailyStepsEntity::class], version = 1)
@TypeConverters(DateConverter::class, UUIDConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun walkDao(): WalkDao
    abstract fun creditsDao(): CreditDao
    abstract fun inventoryDao(): InventoryDao
    abstract fun companionDao(): CompanionDao
    abstract fun dailyStepsDao(): DailyStepsDao
}