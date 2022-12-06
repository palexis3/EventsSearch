package com.example.eventsearch.data.repository

import com.example.eventsearch.data.local.EventDao
import com.example.eventsearch.data.local.EventUi
import com.example.eventsearch.data.local.toEventUi
import com.example.eventsearch.data.model.toEvent
import com.example.eventsearch.data.remote.EventsApi
import javax.inject.Inject
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class SearchRepositoryImpl @Inject constructor(
    private val api: EventsApi,
    private val dao: EventDao
) : SearchRepository {

    override fun search(keyword: String): Flow<List<EventUi>> =
        flow {
            coroutineScope {
                launch {
                    refresh(keyword)
                }

                val dbFlow: Flow<List<EventUi>> = dao
                    .getEvents(keyword)
                    .map { list ->
                        list.map { it.toEventUi() }
                    }

                dbFlow.collect {
                    emit(it)
                }
            }
        }

    override suspend fun refresh(keyword: String) {
        val eventRemote = api.search(keyword)

        if (eventRemote._embedded != null) {
            eventRemote._embedded.events.map { item ->
                item.toEvent(keyword)
            }.also { events ->
                dao.insertEvents(events)
            }
        }
    }
}
