package com.example.eventsearch.utils

import app.cash.turbine.FlowTurbine
import app.cash.turbine.test
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.UnconfinedTestDispatcher

/**
 * CashApp's Turbine library to test Flows ignores the dispatcher used for the tests
 * therefore this extension changes the context of the flow to run on the appropriate
 * dispatcher.
 * More info: https://levelup.gitconnected.com/unit-test-with-kotlin-flow-7e6f675e5b14
 */
@OptIn(ExperimentalCoroutinesApi::class)
suspend fun <T> Flow<T>.testWithScheduler(
    timeoutMs: Long = 1000,
    validate: suspend FlowTurbine<T>.() -> Unit
) {
    val testScheduler = coroutineContext[TestCoroutineScheduler]
    return if (testScheduler == null) {
        test(validate)
    } else {
        flowOn(UnconfinedTestDispatcher(testScheduler))
            .test(validate)
    }
}
