package com.example.eventsearch.data.repository

import com.example.eventsearch.data.model.SearchResponse
import com.example.eventsearch.data.remote.EventsApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SearchRepositoryImpl @Inject constructor(
    private val api: EventsApi
) : SearchRepository {

    override fun search(keyword: String): Flow<SearchResponse> =
        flow { emit(api.search(keyword)) }
}
