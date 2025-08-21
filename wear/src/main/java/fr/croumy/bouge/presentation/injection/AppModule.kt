package fr.croumy.bouge.presentation.injection

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.data.AppDatabase

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
}