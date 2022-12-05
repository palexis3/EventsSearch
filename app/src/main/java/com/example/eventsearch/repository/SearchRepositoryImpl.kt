package com.example.eventsearch.repository

import com.example.eventsearch.model.SearchResponse
import com.example.eventsearch.networking.EventsApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl @Inject constructor(
    private val api: EventsApi
) : SearchRepository {

    override fun search(keyword: String): Flow<SearchResponse> =
        flow { api.search(keyword) }
}
