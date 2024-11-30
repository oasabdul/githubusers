package com.moneyfwd.profile.repository

import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.RepositoryItem
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserProfileResponse
import com.moneyfwd.domain.profile.repository.GetUserRepositoriesResponse
import com.moneyfwd.domain.profile.repository.ProfileRepository
import com.moneyfwd.profile.network.ProfileApiService
import retrofit2.HttpException

class ProfileRepositoryImpl(private val apiService: ProfileApiService) : ProfileRepository {
    override suspend fun getUserRepositories(username: Username): GetUserRepositoriesResponse {
        return userRepositoriesDummy()
        try {
            val response = apiService.getUserRepositories(username)

            //according to requirements, forked repositories should not be included
            val filteredResponse = response.filterNot { it.fork }

            return GetUserRepositoriesResponse.Success(userRepositories = filteredResponse.map { it.mapToDomainModel() })
        } catch (httpException: HttpException) {
            return GetUserRepositoriesResponse.NetworkError
        } catch (exception: Exception) {
            return GetUserRepositoriesResponse.UnknownError
        }
    }

    override suspend fun getUserProfile(username: Username): GetUserProfileResponse {
        return userProfileDummy()
        try {
            val profileResponse = apiService.getUserProfile(username)
            return GetUserProfileResponse.Success(userProfileDetails = profileResponse.mapToDomainModel())
        } catch (httpException: HttpException) {
            return GetUserProfileResponse.NetworkError
        } catch (exception: Exception) {
            return GetUserProfileResponse.UnknownError
        }
    }

    private fun userRepositoriesDummy() = GetUserRepositoriesResponse.Success(
        userRepositories = listOf(
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
            RepositoryItem(name = "Sample repo name", language = "Java", stars = 105, description = "Shows an example repo based on assignment given", htmlUrl = "https://github.com/login"),
        )
    )

    private fun userProfileDummy() = GetUserProfileResponse.Success(
        userProfileDetails = ProfileDetails(profilePictureUrl = "https://i.pinimg.com/originals/76/80/4f/76804f67ba38f85e4802d250e5b15504.jpg", username = "abdulIntija", fullName = "Mark Pain", followerCount = 236, followingCount = 32)
    )
}