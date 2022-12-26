package com.example.eventsearch.data.model.details

import com.example.eventsearch.data.model.Dates
import com.example.eventsearch.data.model.Image
import com.example.eventsearch.utils.formatToReadableDate
import com.example.eventsearch.utils.toDate
import com.example.eventsearch.utils.formatToCurrency
import java.util.Locale

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
    val imageUrls: List<String>? = images?.mapNotNull { it.url }
    val priceRangesFormatted: List<String>? =
        priceRanges?.map {
            "$${formatToCurrency(it.min)} - $${formatToCurrency(it.max)} ${it.currency?.uppercase(Locale.getDefault())}" }
}

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

fun EventDetailsResponse.toEventDetailsUi() = EventDetailsUi(
    name = this.name,
    description = this.description,
    imageUrls = this.imageUrls,
    date = this.readableDate,
    priceRanges = priceRangesFormatted
)
