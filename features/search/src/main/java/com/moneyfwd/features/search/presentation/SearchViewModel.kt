package com.moneyfwd.features.search.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.model.SearchUserItem
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase.Result.Success
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase.Result.Failure
import com.moneyfwd.shared.navigation.NavDestination.ProfileScreen
import com.moneyfwd.shared.navigation.Navigator
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

private const val SEARCH_DELAY_MILLISECONDS = 1000L

class SearchViewModel(
    private val searchGithubUsersUseCase: SearchGithubUsersUseCase,
    private val navigator: Navigator
) : ViewModel() {
    val _viewState = MutableStateFlow(SearchViewState())
    private val viewState: StateFlow<SearchViewState> = _viewState
    private var searchJob: Job? = null

    private fun updateViewState(newState: SearchViewState) {
        _viewState.value = newState
    }

    private fun performSearch(query: String) = viewModelScope.launch {
        updateViewState(viewState.value.copy(loading = true))

        when (val search = searchGithubUsersUseCase(searchQuery = SearchQuery(query))) {
            is Success -> updateViewState(
                viewState.value.copy(
                    loading = false,
                    searchResults = search.searchResult
                )
            )

            is Failure -> updateViewState(
                viewState.value.copy(
                    loading = false,
                    searchResults = emptyList()
                )
            )
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        updateViewState(viewState.value.copy(searchQuery = newQuery))

        // Cancel any existing search query job
        searchJob?.cancel()

        // Start a new search query job 1 second after user stops typing
        searchJob = viewModelScope.launch {
            delay(SEARCH_DELAY_MILLISECONDS)
            if (viewState.value.searchQuery.isNotEmpty()) {
                performSearch(viewState.value.searchQuery)
            }
        }
    }

    fun onUserItemClicked(userItem: SearchUserItem) {
        navigator.navigateTo(ProfileScreen.createRoute(userItem.name))
    }
}