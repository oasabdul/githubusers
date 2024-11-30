package com.moneyfwd.domain.search.repository

import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.model.SearchUserItem

interface SearchUsersRepository {
    suspend fun searchUsers(searchQuery: SearchQuery): SearchUserRepositoryResponse
}

sealed class SearchUserRepositoryResponse {
    data class Success(val searchResults: List<SearchUserItem>) : SearchUserRepositoryResponse()
    data object NetworkError : SearchUserRepositoryResponse()
    data object UnknownError : SearchUserRepositoryResponse()
}