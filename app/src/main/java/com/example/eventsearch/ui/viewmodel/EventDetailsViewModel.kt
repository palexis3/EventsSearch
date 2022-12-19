package com.example.eventsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.eventsearch.data.model.details.EventDetailsUi
import com.example.eventsearch.data.repository.details.DetailsRepository
import com.example.eventsearch.utils.Result
import com.example.eventsearch.utils.asResult
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface EventDetailsUiState {
    object Loading : EventDetailsUiState
    object Error : EventDetailsUiState
    data class Success(val detailsUi: EventDetailsUi) : EventDetailsUiState
}

class EventDetailsViewModel @Inject constructor(
    private val detailsRepository: DetailsRepository
) : ViewModel() {

    private val _eventDetailsUiState =
        MutableStateFlow<EventDetailsUiState>(EventDetailsUiState.Loading)
    val eventDetailsUiState = _eventDetailsUiState.asStateFlow()

    fun getDetails(id: String) {
        viewModelScope.launch {
            detailsRepository
                .getDetails(id)
                .asResult()
                .collect { result ->
                    val detailsUiState = when (result) {
                        is Result.Loading -> EventDetailsUiState.Loading
                        is Result.Error -> EventDetailsUiState.Error
                        is Result.Success -> {
                            val data = result.data
                            EventDetailsUiState.Success(data)
                        }
                    }

                    _eventDetailsUiState.update { detailsUiState }
                }
        }
    }
}
