package com.moneyfwd.data.search.repository

import com.moneyfwd.data.search.network.SearchApiService
import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse.NetworkError
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse.UnknownError
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse.Success
import com.moneyfwd.domain.search.repository.SearchUsersRepository
import retrofit2.HttpException

class SearchUserRepositoryImpl(private val apiService: SearchApiService): SearchUsersRepository {
    override suspend fun searchUsers(searchQuery: SearchQuery): SearchUserRepositoryResponse {
        try {
            val request = apiService.searchUsers(searchQuery.query)
            return Success(searchResults = request.items.map { it.mapToDomainModel() })
        } catch (httpException: HttpException) {
            return NetworkError
        } catch (exception: Exception) {
            return UnknownError
        }
    }
}