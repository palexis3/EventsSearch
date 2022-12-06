package com.example.eventsearch.data.local

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity

/** Note:
    Multiple keywords can point to the same articles so we can’t rely on using the article’s id as
    the identifier because an article may be tagged to multiple keywords so we’ll use a composite key
    to ensure duplicate events are being tied to individual keywords
**/
@Entity(primaryKeys = ["id", "keyword"])
data class Event(
    @NonNull @ColumnInfo(name = "id") val id: String,
    @NonNull @ColumnInfo(name= "keyword") val keyword: String,
    @NonNull @ColumnInfo(name = "name") val name: String,
    @NonNull @ColumnInfo(name = "readable_date") val readableDate: String,
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
