package com.moneyfwd.domain.search.usecase

import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.model.SearchUserItem
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse
import com.moneyfwd.domain.search.repository.SearchUsersRepository

class SearchGithubUsersUseCase(private val repository: SearchUsersRepository) {
    suspend operator fun invoke(searchQuery: SearchQuery): Result {
        return when (val response = repository.searchUsers(searchQuery)) {
            is SearchUserRepositoryResponse.Success -> Result.Success(response.searchResults)
            else -> Result.Failure
        }
    }

    sealed class Result {
        data class Success(val searchResult: List<SearchUserItem>) : Result()
        data object Failure : Result()
    }
}