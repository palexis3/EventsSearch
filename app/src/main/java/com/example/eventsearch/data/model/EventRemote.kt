package com.example.eventsearch.data.model

import com.example.eventsearch.data.local.Event
import com.example.eventsearch.helper.formatToReadableDate
import com.example.eventsearch.helper.toDate

private const val IMAGE_TYPE = "PORTRAIT"

data class EventRemote(
    val id: String,
    val name: String,
    val images: List<Image>,
    val dates: Dates
) {
    val readableDate: String = dates.start.localDate.toDate().formatToReadableDate()
    val imageUrl: String? = images.filter { it.url?.contains(IMAGE_TYPE) ?: false }[0].url
}

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
    val _embedded: EventsList?
)

fun EventRemote.toEvent(keyword: String) = Event(
    keyword = keyword,
    id = this.id,
    name = this.name,
    readableDate = this.readableDate,
    imageUrl = this.imageUrl
)
