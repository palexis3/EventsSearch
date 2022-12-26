package com.example.eventsearch.data.model.details

data class EventDetailsUi(
    val name: String?,
    val description: String?,
    val imageUrls: List<String>?,
    val date: String?,
    val priceRanges: List<String>?
)
