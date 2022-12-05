package com.example.eventsearch.networking

import com.example.eventsearch.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    @GET(".")
    suspend fun search(
        @Query("keyword") keyword: String
    ) : SearchResponse
}