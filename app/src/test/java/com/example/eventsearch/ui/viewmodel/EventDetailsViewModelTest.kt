package com.example.eventsearch.ui.viewmodel

import com.example.eventsearch.data.repository.TestDetailsRepository
import com.example.eventsearch.utils.MainDispatcherRule
import com.example.eventsearch.utils.testWithScheduler
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EventDetailsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val detailsRepository = TestDetailsRepository()
    private lateinit var viewModel: EventDetailsViewModel

    @Before
    fun setup() {
        viewModel = EventDetailsViewModel(detailsRepository)
    }

    @Test
    fun `when eventDetailsUiState is instantiated then show loading ui state`() = runTest {
        viewModel.eventDetailsUiState.testWithScheduler {
            assertTrue(awaitItem() is EventDetailsUiState.Loading)
        }
    }
}