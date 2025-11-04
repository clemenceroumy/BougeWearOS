package fr.croumy.bouge.presentation.injection

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.croumy.bouge.presentation.data.AppDatabase
import fr.croumy.bouge.presentation.extensions.dataStore

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun context(application: Application): Context {
        return application
    }

    @Provides
    fun provideDb(context: Application): AppDatabase = Room
        .databaseBuilder(
            context,
            AppDatabase::class.java, "database"
        )
        .allowMainThreadQueries()
        .build()

    @Provides
    fun provideDataStore(context: Application): DataStore<Preferences> {
        return context.dataStore
    }
}