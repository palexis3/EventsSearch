package com.example.eventsearch.model

data class Event(
    val name: String?,
    val type: String?,
    val id: String?,
    val url: String?,
    val images: List<Image>
)

// TODO: Get first image where the url is not null
data class Image(
    val url: String?
)

data class EventsList(
    val events: List<Event>
)

data class SearchResponse(
    val _embedded: EventsList
)

