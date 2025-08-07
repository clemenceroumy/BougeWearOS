package fr.croumy.bouge.presentation.injection

import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.croumy.bouge.presentation.MainActivity
import fr.croumy.bouge.presentation.data.AppDatabase

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideDb(): AppDatabase = Room
        .databaseBuilder(
            MainActivity.context,
            AppDatabase::class.java, "database"
        )
        .allowMainThreadQueries()
        .build()
}