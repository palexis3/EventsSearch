package com.example.eventsearch.data.repository.details

import com.example.eventsearch.data.model.details.EventDetailsUi
import kotlinx.coroutines.flow.Flow

interface DetailsRepository {
    fun getDetails(id: String): Flow<EventDetailsUi>
}
