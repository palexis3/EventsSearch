package com.example.eventsearch.ui.navigation

import androidx.navigation.NavHostController

fun NavHostController.navigateToRoute(route: String) =
    this.navigate(route) {
        launchSingleTop = true
    }

fun NavHostController.navigateToDetailsScreen(id: String) {
    this.navigateToRoute("${Details.route}/$id")
}
