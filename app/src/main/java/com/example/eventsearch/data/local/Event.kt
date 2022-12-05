package com.example.eventsearch.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Event(
    @PrimaryKey val keyword: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "readable_date") val readableDate: String,
    @ColumnInfo(name = "image_url") val imageUrl: String?
)

fun Event.toEventUi() = EventUi(
    name = this.name,
    readableDate = this.readableDate,
    imageUrl = this.imageUrl
)

// EventUi is the data model consumed at the UI layer
data class EventUi(
    val name: String,
    val readableDate: String,
    val imageUrl: String?,
)
