package com.example.eventsearch.networking

import com.example.eventsearch.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    @GET("/discovery/v2/events.json?city=San Francisco&apikey=")
    suspend fun search(
        @Query("keyword") keyword: String
    ) : SearchResponse
}