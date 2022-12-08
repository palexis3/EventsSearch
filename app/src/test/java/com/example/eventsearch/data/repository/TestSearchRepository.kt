package com.example.eventsearch.data.repository

import com.example.eventsearch.data.model.EventUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestSearchRepository : SearchRepository {
    /**
     * A hot testing flow to emit list of EventUi models
     */
    private val eventUiFlow: MutableSharedFlow<List<EventUi>> = MutableSharedFlow(replay = 1)

    override fun search(keyword: String): Flow<List<EventUi>> = eventUiFlow

    /**
     * No operation for testing since this is a network call
     */
    override suspend fun refresh(keyword: String) {}

    /**
     * A testing function to allow the control of list of EventUi models
     */
    suspend fun sendEventUis(eventUis: List<EventUi>) {
        eventUiFlow.emit(eventUis)
    }
}