package fr.croumy.bouge.presentation.injection

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.data.dao.InventoryDao
import fr.croumy.bouge.presentation.extensions.dataStore

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun context(application: Application): Context {
        return application
    }

    @Provides
    fun provideDb(context: Application): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "database"
            )
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    db.execSQL(InventoryDao.INSERT_DEFAULT_BACKGROUND)
                }
            })
            .build()
    }

    @Provides
    fun provideDataStore(context: Application): DataStore<Preferences> {
        return context.dataStore
    }
}