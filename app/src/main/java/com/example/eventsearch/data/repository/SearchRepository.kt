package com.example.eventsearch.data.repository

import com.example.eventsearch.data.model.EventUi
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun refresh(keyword: String)
    fun search(keyword: String): Flow<List<EventUi>>
}
