package com.moneyfwd.domain.profile.usecase

import com.moneyfwd.domain.profile.model.RepositoryItem
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserRepositoriesResponse
import com.moneyfwd.domain.profile.repository.ProfileRepository

open class GetUserRepositoriesUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(username: Username): Result {
        return when (val response = repository.getUserRepositories(username)) {
            is GetUserRepositoriesResponse.Success -> Result.Success(response.userRepositories)
            else -> Result.Failure
        }
    }

    sealed class Result {
        data class Success(val repositories: List<RepositoryItem>) : Result()
        data object Failure : Result()
    }
}