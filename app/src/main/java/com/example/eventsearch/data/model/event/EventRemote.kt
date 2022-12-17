package com.example.eventsearch.data.model.event

import com.example.eventsearch.data.model.Dates
import com.example.eventsearch.data.model.IMAGE_TYPE
import com.example.eventsearch.data.model.Image
import com.example.eventsearch.utils.formatToReadableDate
import com.example.eventsearch.utils.toDate


/**
 * EventRemote is the data model fetched from EventsApi.
 */
data class EventRemote(
    val id: String?,
    val name: String?,
    val images: List<Image>?,
    val dates: Dates?
) {
    val readableDate: String? = dates?.start?.localDate?.toDate()?.formatToReadableDate()
    val imageUrl: String? = images?.filter { it.url?.contains(IMAGE_TYPE) ?: false }?.get(0)?.url
}

data class EventsList(
    val events: List<EventRemote>?
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
