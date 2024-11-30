package com.moneyfwd.domain.profile.usecase

import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserProfileResponse
import com.moneyfwd.domain.profile.repository.ProfileRepository

open class GetUserProfileDetailsUseCase(private val repository: ProfileRepository) {
    suspend operator fun invoke(username: Username): Result {
        return when (val response = repository.getUserProfile(username)) {
            is GetUserProfileResponse.Success -> Result.Success(response.userProfileDetails)
            else -> Result.Failure
        }
    }

    sealed class Result {
        data class Success(val profileDetails: ProfileDetails) : Result()
        data object Failure : Result()
    }
}