package com.moneyfwd.features.search.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.moneyfwd.features.search.R
import com.moneyfwd.features.search.presentation.SearchViewModel

@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val viewState = viewModel._viewState.collectAsState().value

    Column {
        // Search Bar
        Icon(
            painter = painterResource(id = R.drawable.github_icon),
            contentDescription = "Search Icon",
            modifier = Modifier
                .size(80.dp)
                .padding(top = 30.dp)
                .align(Alignment.CenterHorizontally)
        )
        TextField(
            value = viewState.searchQuery,
            onValueChange = { viewModel.onSearchQueryChanged(it) },
            label = { Text("Search GitHub Users") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )

        // List displaying search results
        if (viewState.loading) {
            SearchResultLoadingComposition()
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(viewState.searchResults.size) { itemIndex ->
                    val userItem = viewState.searchResults[itemIndex]
                    UserRowComposition(
                        modifier = Modifier.clickable(onClick = {
                            viewModel.onUserItemClicked(
                                userItem
                            )
                        }),
                        userItem = userItem
                    )
                }
            }
        }
    }
}