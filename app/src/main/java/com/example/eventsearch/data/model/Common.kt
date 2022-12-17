package com.example.eventsearch.data.model

const val IMAGE_TYPE = "PORTRAIT"

data class Dates(
    val start: LocalDate?
)

data class LocalDate(
    val localDate: String?
)

data class Image(
    val url: String?
)
