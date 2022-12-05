package com.example.eventsearch.networking

import com.example.eventsearch.BuildConfig
import com.example.eventsearch.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface EventsApi {

    @GET("/discovery/v2/events.json?city=San Francisco&apikey=${BuildConfig.API_KEY}")
    suspend fun search(
        @Query("keyword") keyword: String
    ) : SearchResponse
}
