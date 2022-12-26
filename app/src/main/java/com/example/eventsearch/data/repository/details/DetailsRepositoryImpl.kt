package com.example.eventsearch.data.repository.details

import com.example.eventsearch.data.model.details.EventDetailsUi
import com.example.eventsearch.data.model.details.toEventDetailsUi
import com.example.eventsearch.data.remote.EventsApi
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DetailsRepositoryImpl @Inject constructor(
    private val api: EventsApi
) : DetailsRepository {

    override fun getDetails(id: String): Flow<EventDetailsUi> = flow {
        val eventDetailsResponse = api.getDetails(id)
        val eventDetailsUi = eventDetailsResponse.toEventDetailsUi()
        emit(eventDetailsUi)
    }
}
