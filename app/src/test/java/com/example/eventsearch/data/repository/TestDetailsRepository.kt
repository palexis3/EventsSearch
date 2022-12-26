package com.example.eventsearch.data.repository

import com.example.eventsearch.data.model.details.EventDetailsUi
import com.example.eventsearch.data.repository.details.DetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class TestDetailsRepository : DetailsRepository {

    private val detailsUiFlow: MutableSharedFlow<EventDetailsUi> = MutableSharedFlow(replay = 1)

    override fun getDetails(id: String): Flow<EventDetailsUi> = detailsUiFlow

    suspend fun sendDetailsUi(detailsUi: EventDetailsUi) {
        detailsUiFlow.emit(detailsUi)
    }
}