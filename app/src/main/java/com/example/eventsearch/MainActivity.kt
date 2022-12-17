package com.example.eventsearch

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.eventsearch.ui.composable.SearchScreen
import com.example.eventsearch.ui.navigation.Details
import com.example.eventsearch.ui.navigation.Search
import com.example.eventsearch.ui.theme.EventSearchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@RequiresApi(Build.VERSION_CODES.M)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EventSearchTheme {
                ShowApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun ShowApp() {
    val navController = rememberNavController()

    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Search.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(route = Search.route) {
                SearchScreen()
            }

            composable(
                route = Details.routeWithArgs,
                arguments = Details.arguments
            ) { navBackStackEntry ->
                val id = navBackStackEntry.arguments?.getString(Details.eventIdArg)
                if (id != null) {
                    // TODO: Implement details screen
                }
            }
        }
    }
}
