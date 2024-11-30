package com.moneyfwd.features.search.presentation

import com.moneyfwd.domain.search.model.SearchUserItem

data class SearchViewState(
    val searchQuery: String = "",
    val loading: Boolean = false,
    val searchResults: List<SearchUserItem> = emptyList()
)