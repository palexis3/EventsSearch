package com.example.eventsearch.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface Screen {
    val route: String
}

object Search : Screen {
    override val route = "search"
}

object Details: Screen {
    override val route = "details"
    const val eventIdArg = "event_id"
    val routeWithArgs = "${route}/{${eventIdArg}}"
    val arguments = listOf(
        navArgument(eventIdArg) { type = NavType.StringType }
    )
}
