package com.example.eventsearch.repository

import com.example.eventsearch.model.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun search(keyword: String): Flow<SearchResponse>
}