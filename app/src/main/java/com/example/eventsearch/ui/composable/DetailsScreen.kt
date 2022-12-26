package com.example.eventsearch.ui.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.eventsearch.data.model.details.EventDetailsUi
import com.example.eventsearch.ui.theme.MediumPadding
import com.example.eventsearch.ui.viewmodel.EventDetailsUiState
import com.example.eventsearch.ui.viewmodel.EventDetailsViewModel

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun DetailsScreen(
    id: String,
    closeScreen: () -> Unit,
    detailsViewModel: EventDetailsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = id) {
        if (id.isEmpty().not()) {
            detailsViewModel.getDetails(id.trim())
        }
    }

    val detailsUiState: EventDetailsUiState by detailsViewModel.eventDetailsUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumPadding)
    ) {
        Row {
            IconButton(onClick = closeScreen) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Go Back")
            }
        }

        Spacer(Modifier.height(12.dp))

        ShowDetailsUiState(
            detailsUiState,
            closeScreen
        )
    }
}

@Composable
fun ShowDetailsUiState(
    detailsUiState: EventDetailsUiState,
    closeScreen: () -> Unit
) {
    when(detailsUiState) {
        is EventDetailsUiState.Error -> {
           closeScreen.invoke()
        }
        is EventDetailsUiState.Loading -> {
            Box {
                CircularProgressIndicator()
            }
        }
        is EventDetailsUiState.Success -> {
            val detailsUi = detailsUiState.detailsUi
            LazyColumn {
                item {
                    ShowDetailsUi(detailsUi)
                }
            }
        }
    }
}

@Composable
fun ShowDetailsUi(
    detailsUi: EventDetailsUi
) {
    detailsUi.imageUrls?.let { urls ->
        if(urls.isNotEmpty()) {
            LazyRow {
                items(urls) { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "Event Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(200.dp)
                            .width(200.dp)
                            .padding(end = 6.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
    }

    detailsUi.name?.let { name ->
        if (name.isNotEmpty()) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
    }

    detailsUi.date?.let { date ->
        if (date.isNotEmpty()) {
            Text(text = date, style = MaterialTheme.typography.labelMedium)
        }
        Spacer(modifier = Modifier.height(4.dp))
    }

    detailsUi.description?.let { description ->
        if (description.isNotEmpty()) {
            Text(text = description, style = MaterialTheme.typography.bodyMedium)
        }
    }

    detailsUi.priceRanges?.let { priceRanges ->
        if (priceRanges.isNotEmpty()) {
            Text(
                text = priceRanges[0],
                style = MaterialTheme.typography.bodySmall
            )
        }
    }

}
