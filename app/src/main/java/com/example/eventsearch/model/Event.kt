package com.example.eventsearch.model

import com.example.eventsearch.helper.formatToReadableDate
import com.example.eventsearch.helper.toDate

data class Event(
    val name: String,
    val type: String?,
    val id: String?,
    val url: String?,
    val images: List<Image>,
    val dates: Dates
) {
    val readableDate: String = dates.start.localDate.toDate().formatToReadableDate()
    val imageUrl: String? = images.filter { it.url?.contains("PORTRAIT") ?: false }[0].url
}

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
