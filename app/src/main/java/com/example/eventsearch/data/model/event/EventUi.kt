package com.example.eventsearch.data.model.event

/**
 * EventUi is the data model consumed at the UI layer
 */
data class EventUi(
    val id: String,
    val name: String?,
    val readableDate: String?,
    val imageUrl: String?,
)
