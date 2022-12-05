package com.example.eventsearch.data.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): EventDatabase =
        Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "event-database"
        ).build()

    @Provides
    fun provideEventsDao(eventDatabase: EventDatabase): EventDao =
        eventDatabase.eventDao()
}
