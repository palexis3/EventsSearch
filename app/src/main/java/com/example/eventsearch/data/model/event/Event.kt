package com.example.eventsearch.data.model.event

import androidx.room.ColumnInfo
import androidx.room.Entity

/**
  Note:
     1) Event is the data model that represents a table in the 'event' database and as such is
        consumed at the database layer.

     2) Multiple keywords can point to the same articles so we can’t rely on using the article’s id as
        the identifier because an article may be tagged to multiple keywords so we’ll use a composite key
        consisting of [id & keyword] to ensure duplicate events are being tied to individual keywords
*/

@Entity(primaryKeys = ["id", "keyword"])
data class Event(
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name= "keyword") val keyword: String,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "readable_date") val readableDate: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?
)

fun Event.toEventUi() = EventUi(
    id = this.id,
    name = this.name,
    readableDate = this.readableDate,
    imageUrl = this.imageUrl
)
