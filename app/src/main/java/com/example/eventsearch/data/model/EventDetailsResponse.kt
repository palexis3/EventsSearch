package com.example.eventsearch.data.model

import com.example.eventsearch.utils.formatToReadableDate
import com.example.eventsearch.utils.toDate

private const val IMAGE_TYPE = "PORTRAIT"

data class EventDetailsResponse(
    val id: String?,
    val _embedded: Embedded?,
    val name: String?,
    val description: String?,
    val images: List<Image>?,
    val priceRanges: List<PriceRange>?,
    val dates: Dates?
) {
    val readableDate: String? = dates?.start?.localDate?.toDate()?.formatToReadableDate()
    val imageUrl: String? = images?.filter { it.url?.contains(IMAGE_TYPE) ?: false }?.get(0)?.url
}

data class EventDetailsUi(
    val name: String?,
    val description: String?,
    val image: String?,
    val date: String?
)

fun EventDetailsResponse.toEventDetailsUi() = EventDetailsUi(
    name = this.name,
    description = this.description,
    image = this.imageUrl,
    date = this.readableDate
)

data class Venue(
    val id: String?,
    val name: String?,
    val description: String?
)

data class PriceRange(
    val currency: String?,
    val min: Float?,
    val max: Float?
)

data class Embedded(
    val venues: List<Venue>?
)