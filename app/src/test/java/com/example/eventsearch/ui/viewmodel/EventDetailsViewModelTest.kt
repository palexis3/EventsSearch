package com.example.eventsearch.ui.viewmodel

import com.example.eventsearch.data.model.details.EventDetailsUi
import com.example.eventsearch.data.repository.TestDetailsRepository
import com.example.eventsearch.utils.MainDispatcherRule
import com.example.eventsearch.utils.testWithScheduler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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

    @Test
    fun `when event id is correct then show success ui state`() = runTest {
        viewModel.eventDetailsUiState.testWithScheduler {
            assertTrue(awaitItem() is EventDetailsUiState.Loading)

            viewModel.getDetails(COMEDY_ID)
            detailsRepository.sendDetailsUi(detailUiForComedy)

            assertEquals(detailUiForComedy, (awaitItem() as EventDetailsUiState.Success).detailsUi)
        }
    }

    @Test
    fun `when multiple event ids are called then show success ui state in order`() = runTest {
        viewModel.eventDetailsUiState.testWithScheduler {
            awaitItem()

            viewModel.getDetails(COMEDY_ID)
            detailsRepository.sendDetailsUi(detailUiForComedy)

            assertEquals(detailUiForComedy, (awaitItem() as EventDetailsUiState.Success).detailsUi)

            viewModel.getDetails(SPORT_ID)
            detailsRepository.sendDetailsUi(detailUiForSport)

            assertTrue(awaitItem() is EventDetailsUiState.Loading)

            assertEquals(detailUiForSport, (awaitItem() as EventDetailsUiState.Success).detailsUi)
        }
    }

    companion object {
        const val COMEDY_ID = "comedy_id"
        const val SPORT_ID = "sport_id"

        val detailUiForComedy = EventDetailsUi(
            "comedy",
            "comedy show",
            imageUrls = listOf("/comedy-show.jpg"),
            "todays date",
            priceRanges = listOf("$30-$70")
        )

        val detailUiForSport = EventDetailsUi(
            "sport",
            "sport event",
            imageUrls = listOf("/sport-event.jpg"),
            "todays date",
            priceRanges = listOf("$30-$70")
        )
    }
}
