package com.example.eventsearch.data.repository

import com.example.eventsearch.data.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(keyword: String): Flow<SearchResponse>
}
