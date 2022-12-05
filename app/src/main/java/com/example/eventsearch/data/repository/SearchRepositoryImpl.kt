package com.example.eventsearch.data.repository

import com.example.eventsearch.data.local.EventDao
import com.example.eventsearch.data.local.EventUi
import com.example.eventsearch.data.local.toEventUi
import com.example.eventsearch.data.model.toEvent
import com.example.eventsearch.data.remote.EventsApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

class SearchRepositoryImpl @Inject constructor(
    private val api: EventsApi,
    private val dao: EventDao
) : SearchRepository {

    override fun search(keyword: String): Flow<List<EventUi>> {
        return dao.getEvents(keyword).map { events ->
            events.map { it.toEventUi() }
        }.onEach { list ->
            if (list.isEmpty()) {
                fetchFromApi(keyword)
            }
        }
    }

    override suspend fun fetchFromApi(keyword: String) {
        api.search(keyword)._embedded.events.map {
            it.toEvent(keyword)
        }.also { events ->
            dao.insertEvents(events)
        }
    }
}
