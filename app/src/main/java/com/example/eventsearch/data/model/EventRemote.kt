package com.example.eventsearch.data.model

import com.example.eventsearch.data.local.Event
import com.example.eventsearch.helper.formatToReadableDate
import com.example.eventsearch.helper.toDate

data class EventRemote(
    val name: String,
    val images: List<Image>,
    val dates: Dates
) {
    val readableDate: String = dates.start.localDate.toDate().formatToReadableDate()
    val imageUrl: String? = images.filter { it.url?.contains("PORTRAIT") ?: false }[0].url
}

fun EventRemote.toEvent(keyword: String) = Event(
    keyword = keyword,
    name = this.name,
    readableDate = this.readableDate,
    imageUrl = this.imageUrl
)

data class Image(
    val url: String?
)

data class EventsList(
    val events: List<EventRemote>
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
