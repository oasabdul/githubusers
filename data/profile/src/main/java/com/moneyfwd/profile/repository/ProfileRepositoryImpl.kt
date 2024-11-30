package com.moneyfwd.profile.repository

import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserProfileResponse
import com.moneyfwd.domain.profile.repository.GetUserRepositoriesResponse
import com.moneyfwd.domain.profile.repository.ProfileRepository
import com.moneyfwd.profile.network.ProfileApiService
import retrofit2.HttpException

class ProfileRepositoryImpl(private val apiService: ProfileApiService) : ProfileRepository {
    override suspend fun getUserRepositories(username: Username): GetUserRepositoriesResponse {
        try {
            val response = apiService.getUserRepositories(username.value)

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
        try {
            val profileResponse = apiService.getUserProfile(username.value)
            return GetUserProfileResponse.Success(userProfileDetails = profileResponse.mapToDomainModel())
        } catch (httpException: HttpException) {
            return GetUserProfileResponse.NetworkError
        } catch (exception: Exception) {
            return GetUserProfileResponse.UnknownError
        }
    }
}