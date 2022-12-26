package com.example.eventsearch.data.repository.search

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.eventsearch.data.local.EventDao
import com.example.eventsearch.data.model.event.EventUi
import com.example.eventsearch.data.model.event.toEvent
import com.example.eventsearch.data.model.event.toEventUi
import com.example.eventsearch.data.remote.EventsApi
import com.example.eventsearch.utils.WifiService
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchRepositoryImpl @Inject constructor(
    private val api: EventsApi,
    private val dao: EventDao,
    private val wifiService: WifiService
) : SearchRepository {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun search(keyword: String): Flow<List<EventUi>> =
        flow {
            coroutineScope {
                launch {
                    if (wifiService.isOnline()) {
                        refresh(keyword)
                    }
                }

                val dbFlow: Flow<List<EventUi>> = dao
                    .getEvents(keyword)
                    .distinctUntilChanged()
                    .map { list ->
                        list.map { it.toEventUi() }
                    }

                dbFlow.collect {
                    emit(it)
                }
            }
        }

    override suspend fun refresh(keyword: String) = withContext(Dispatchers.IO) {
        val eventRemote = api.search(keyword)

        if (eventRemote._embedded?.events != null) {
            eventRemote._embedded.events.map { item ->
                item.toEvent(keyword)
            }.also { events ->
                dao.insertEvents(events)
            }
        }
    }
}
