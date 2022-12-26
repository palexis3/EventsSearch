package com.example.eventsearch.ui.viewmodel

import com.example.eventsearch.data.model.event.EventUi
import com.example.eventsearch.data.repository.TestSearchRepository
import com.example.eventsearch.utils.MainDispatcherRule
import com.example.eventsearch.utils.testWithScheduler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchEventsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val searchRepository = TestSearchRepository()

    private lateinit var viewModel: SearchEventsViewModel

    @Before
    fun setup() {
        viewModel = SearchEventsViewModel(searchRepository)
    }

    @Test
    fun `when searchListState is instantiated then show uninitialized ui state`() = runTest {
        viewModel.searchListUiState.testWithScheduler {
            assertTrue(awaitItem() is SearchListUiState.Uninitialized)
        }
    }

    @Test
    fun `when query made with no result then show error ui state`() = runTest {
        viewModel.search(NO_RESULTS)
        searchRepository.sendEventUis(emptyList())

        viewModel.searchListUiState.testWithScheduler {
            assertTrue(awaitItem() is SearchListUiState.Uninitialized)
            assertTrue(awaitItem() is SearchListUiState.Loading)
            assertTrue(awaitItem() is SearchListUiState.Error)
        }
    }

    @Test
    fun `when query made with valid keyword then show success list ui state`() = runTest {
        viewModel.search(COMEDY)
        searchRepository.sendEventUis(eventUisForComedy)

        viewModel.searchListUiState.testWithScheduler {
            // Skip initial Uninitialized and Loading states
            awaitItem()
            awaitItem()

            assertEquals(eventUisForComedy, (awaitItem() as SearchListUiState.Success).eventUis)
        }
    }

    @Test
    fun `when multiple valid queries with successful list ui states then ensure most recent consumed`() =
        runTest {
            viewModel.search(COMEDY)
            searchRepository.sendEventUis(eventUisForComedy)

            viewModel.search(SPORT)
            searchRepository.sendEventUis(eventUisForSport)

            viewModel.searchListUiState.testWithScheduler {
                // Skip initial Uninitialized and Loading states
                awaitItem()
                awaitItem()

                assertEquals(
                    eventUisForSport,
                    (expectMostRecentItem() as SearchListUiState.Success).eventUis
                )
            }
        }


    @Test
    fun `when multiple valid queries with successful list ui states then ensure oldest not consumed`() =
        runTest {
            viewModel.search(COMEDY)
            searchRepository.sendEventUis(eventUisForComedy)

            viewModel.search(SPORT)
            searchRepository.sendEventUis(eventUisForSport)

            viewModel.searchListUiState.testWithScheduler {
                // Skip initial Uninitialized and Loading states
                awaitItem()
                awaitItem()

                assertNotEquals(
                    eventUisForComedy,
                    (expectMostRecentItem() as SearchListUiState.Success).eventUis
                )
            }
        }

    @Test
    fun `when multiple valid queries with successful list ui states then ensure they are consumed sequentially`() =
        runTest {
            viewModel.search(COMEDY)
            searchRepository.sendEventUis(eventUisForComedy)

            viewModel.searchListUiState.testWithScheduler {
                // Skip initial Uninitialized and Loading states
                awaitItem()
                awaitItem()

                assertEquals(eventUisForComedy, (awaitItem() as SearchListUiState.Success).eventUis)

                viewModel.search(SPORT)
                searchRepository.sendEventUis(eventUisForSport)

                // Another Loading state to consume above search statement
                awaitItem()

                assertEquals(eventUisForSport, (awaitItem() as SearchListUiState.Success).eventUis)
            }
        }

    @Test
    fun `when valid query with successful ui state followed by query with no results then show consumed sequentially`() =
        runTest {
            viewModel.search(SPORT)
            searchRepository.sendEventUis(eventUisForSport)

            viewModel.searchListUiState.testWithScheduler {
                // Skip initial Uninitialized and Loading states
                awaitItem()
                awaitItem()

                assertEquals(eventUisForSport, (awaitItem() as SearchListUiState.Success).eventUis)

                viewModel.search(NO_RESULTS)
                searchRepository.sendEventUis(emptyList())

                // Another Loading state to consume above search statement
                awaitItem()

                // Empty list is an Error state
                assertTrue(awaitItem() is SearchListUiState.Error)
            }
        }

    companion object {
        const val COMEDY = "comedy"
        const val SPORT = "sport"
        const val NO_RESULTS = "no results"

        val eventUisForComedy = listOf(
            EventUi(
                "comedy-show1",
                "todays-date",
                "/comedy-show1.jpg",
                "image-url1"
            ),
            EventUi(
                "comedy-show2",
                "next-week-date",
                "/comedy-show2.jpg",
                "image-url2"
            ),
            EventUi(
                "comedy-show3",
                "tomorrow-date",
                "/comedy-show3.jpg",
                "image-url3"
            )
        )

        val eventUisForSport = listOf(
            EventUi(
                "sport1",
                "todays-date",
                "/sport1.jpg",
                "image-url1"
            ),
            EventUi(
                "sport2",
                "next-week-date",
                "/sport2.jpg",
                "image-url2"
            ),
            EventUi(
                "sport3",
                "tomorrow-date",
                "/sport3.jpg",
                "image-url3"
            )
        )
    }
}
