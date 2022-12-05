package com.example.eventsearch.model

data class Event(
    val name: String,
    val type: String?,
    val id: String?,
    val url: String?,
    val images: List<Image>,
    val dates: Dates
)

data class Image(
    val url: String?
)

data class EventsList(
    val events: List<Event>
)

data class Dates(
    val start: LocalDate
)

data class LocalDate(
    val localDate: String
)

data class SearchResponse(
    val _embedded: EventsList
)
