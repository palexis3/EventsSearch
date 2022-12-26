package com.example.eventsearch.data.remote

import com.example.eventsearch.BuildConfig
import com.example.eventsearch.data.model.details.EventDetailsResponse
import com.example.eventsearch.data.model.event.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventsApi {

    @GET("/discovery/v2/events.json?city=San Francisco&apikey=${BuildConfig.API_KEY}")
    suspend fun search(
        @Query("keyword") keyword: String
    ) : SearchResponse

    @GET("/discovery/v2/events/{id}?apikey=${BuildConfig.API_KEY}")
    suspend fun getDetails(
        @Path("id") id: String
    ) : EventDetailsResponse
}
