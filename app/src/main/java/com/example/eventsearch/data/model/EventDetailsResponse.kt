package com.example.eventsearch.data.model

data class EventDetailsResponse(
    val id: String?,
    val _embedded: Embedded?,
    val name: String?,
    val description: String?,
    val images: List<Image>,
    val priceRanges: List<PriceRange>?
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