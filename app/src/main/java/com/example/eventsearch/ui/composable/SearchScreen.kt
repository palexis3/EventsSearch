package com.example.eventsearch.ui.composable

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
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
import com.example.eventsearch.data.model.event.EventUi
import com.example.eventsearch.utils.WifiService
import com.example.eventsearch.ui.theme.ExtraSmallPadding
import com.example.eventsearch.ui.theme.MediumPadding
import com.example.eventsearch.ui.theme.SmallPadding
import com.example.eventsearch.ui.viewmodel.SearchEventsViewModel
import com.example.eventsearch.ui.viewmodel.SearchListUiState
import com.example.eventsearch.ui.viewmodel.WifiServiceViewModel

@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun SearchScreen(
    viewModel: SearchEventsViewModel = hiltViewModel(),
    wifiServiceViewModel: WifiServiceViewModel = hiltViewModel(),
    navigateToDetailsScreen: (String) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = query) {
        if (query.isNotEmpty()) {
            viewModel.search(query.trim())
        } else {
            viewModel.resetSearch()
        }
    }

    val listUiState: SearchListUiState by viewModel.searchListUiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(MediumPadding)
    ) {
        SearchTextField(
            modifier = Modifier.fillMaxWidth(),
            query = query,
            onQueryChanged = { value ->
                query = value
            }
        )

        Spacer(Modifier.height(SmallPadding))

        SearchListState(
            listUiState = listUiState,
            wifiService = wifiServiceViewModel.wifiService,
            navigateToDetailsScreen = navigateToDetailsScreen
        )
    }
}


@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun SearchListState(
    listUiState: SearchListUiState,
    wifiService: WifiService,
    navigateToDetailsScreen: (String) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.SpaceEvenly,
        contentPadding = PaddingValues(
            top = MediumPadding,
            bottom = MediumPadding
        )
    ) {
        when (listUiState) {
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
                items(listUiState.eventUis) { eventUi ->
                    EventUiItem(
                        eventUi,
                        wifiService,
                        navigateToDetailsScreen = navigateToDetailsScreen
                    )
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun EventUiItem(
    eventUi: EventUi,
    wifiService: WifiService,
    navigateToDetailsScreen: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .clickable { navigateToDetailsScreen(eventUi.id) }
            .padding(MediumPadding)
    ) {
        if (eventUi.imageUrl.isNullOrEmpty().not() && wifiService.isOnline()) {
            AsyncImage(
                model = eventUi.imageUrl,
                contentDescription = stringResource(id = R.string.event_image),
                modifier = Modifier
                    .fillMaxHeight()
                    .width(150.dp),
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.width(SmallPadding))
        }

        Column {
            if (eventUi.name.isNullOrEmpty().not()) {
                Text(
                    text = eventUi.name!!,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(ExtraSmallPadding))
            }

            if (eventUi.readableDate.isNullOrEmpty().not()) {
                Text(
                    text = eventUi.readableDate!!,
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
        placeholder = { Text(stringResource(id = R.string.search)) },
        onValueChange = { value ->
            onQueryChanged(value)
        },
        leadingIcon = {
            Icon(
                modifier = Modifier.padding(ExtraSmallPadding),
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.search)
            )
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChanged("") }) {
                    Icon(
                        modifier = Modifier.padding(end = ExtraSmallPadding),
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(id = R.string.close)
                    )
                }
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
    )
}
