package com.moneyfwd.domain.profile.repository

import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.RepositoryItem
import com.moneyfwd.domain.profile.model.Username

interface ProfileRepository {
    suspend fun getUserRepositories(username: Username): GetUserRepositoriesResponse
    suspend fun getUserProfile(username: Username): GetUserProfileResponse
}

sealed class GetUserRepositoriesResponse {
    data class Success(val userRepositories: List<RepositoryItem>) : GetUserRepositoriesResponse()
    data object NetworkError : GetUserRepositoriesResponse()
    data object UnknownError : GetUserRepositoriesResponse()
}

sealed class GetUserProfileResponse {
    data class Success(val userProfileDetails: ProfileDetails) : GetUserProfileResponse()
    data object NetworkError : GetUserProfileResponse()
    data object UnknownError : GetUserProfileResponse()
}