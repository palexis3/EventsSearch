package com.example.eventsearch.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.eventsearch.R
import com.example.eventsearch.data.local.EventUi
import com.example.eventsearch.viewmodel.SearchEventsViewModel
import com.example.eventsearch.viewmodel.SearchListUiState

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchEventsViewModel = hiltViewModel()
) {
    var query by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = query) {
        if (query.isNotEmpty()) {
            viewModel.search(query)
        } else {
            viewModel.resetSearch()
        }
    }

    val uiState: SearchListUiState by viewModel.searchListState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChanged = { value ->
                query = value
            }
        )

        Spacer(Modifier.height(8.dp))

        SearchListState(uiState = uiState)
    }
}

@Composable
fun SearchListState(
    uiState: SearchListUiState
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(
            top = 12.dp,
            bottom = 12.dp
        )
    ) {
        when (uiState) {
            // Uninitialized state means we have an empty view
            SearchListUiState.Uninitialized -> {}
            SearchListUiState.Error -> {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        Text(
                            text = stringResource(id = R.string.events_search_error),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.align(Alignment.TopCenter),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            SearchListUiState.Loading -> {
                item {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.TopCenter)
                        )
                    }
                }
            }
            is SearchListUiState.Success -> {
                items(uiState.events) { event ->
                    EventItem(event)
                }
            }
        }
    }
}

@Composable
fun EventItem(
    event: EventUi
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        if (event.imageUrl.isNullOrEmpty().not()) {
            AsyncImage(
                model = event.imageUrl,
                contentDescription = "Event Image",
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.width(8.dp))
        }

        Column {
            Text(
                text = event.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(4.dp))

            if (event.readableDate.isNotEmpty()) {
                Text(
                    text = event.readableDate,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
    Spacer(Modifier.height(1.dp))
    Divider(Modifier.height(2.dp))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchTextField(
    modifier: Modifier,
    query: String,
    onQueryChanged: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = query,
        singleLine = true,
        onValueChange = { value ->
            onQueryChanged(value)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close"
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}
