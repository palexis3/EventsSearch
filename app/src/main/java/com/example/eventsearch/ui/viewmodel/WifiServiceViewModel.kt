package com.example.eventsearch.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.eventsearch.utils.WifiService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Wrapping WifiService in a viewmodel to make it available in the SearchScreen composable
 * since composables are functions and you can't inject dependencies into a function
 */
@HiltViewModel
class WifiServiceViewModel @Inject constructor(
    val wifiService: WifiService
) : ViewModel()
