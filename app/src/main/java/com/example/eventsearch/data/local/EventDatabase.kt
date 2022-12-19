package com.example.eventsearch.data.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.eventsearch.data.model.event.Event

@Database(
    entities = [Event::class],
    version = 2,
    exportSchema = true,
    autoMigrations = [
        AutoMigration(from = 1, to = 2)
    ]
)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDao
}
