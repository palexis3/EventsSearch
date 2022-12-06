package com.example.eventsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventsearch.data.model.EventUi
import com.example.eventsearch.helper.Result
import com.example.eventsearch.helper.asResult
import com.example.eventsearch.data.repository.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface SearchListUiState {
    data class Success(val eventUis: List<EventUi>) : SearchListUiState
    object Error : SearchListUiState
    object Loading : SearchListUiState
    object Uninitialized : SearchListUiState
}

@HiltViewModel
class SearchEventsViewModel @Inject constructor(
    private val searchRepository: SearchRepository
) : ViewModel() {

    private val _searchListState = MutableStateFlow<SearchListUiState>(SearchListUiState.Uninitialized)
    val searchListState = _searchListState.asStateFlow()

    fun search(keyword: String) {
        viewModelScope.launch {
            searchRepository.search(keyword).asResult()
                .collect { result ->
                    val searchState = when (result) {
                        is Result.Success -> {
                            val eventUis = result.data

                            if (eventUis.isNotEmpty()) {
                                SearchListUiState.Success(eventUis)
                            } else {
                                SearchListUiState.Error
                            }
                        }
                        is Result.Loading -> SearchListUiState.Loading
                        is Result.Error -> SearchListUiState.Error
                    }
                    _searchListState.update { searchState }
                }
        }
    }

    fun resetSearch() {
        _searchListState.update { SearchListUiState.Uninitialized }
    }
}
