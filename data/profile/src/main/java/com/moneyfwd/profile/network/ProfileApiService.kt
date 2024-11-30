package com.moneyfwd.profile.network

import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.profile.model.UserProfileResponse
import com.moneyfwd.profile.model.UserRepositoryResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileApiService {
    @GET("users/{username}")
    suspend fun getUserProfile(@Path("username") username: Username): UserProfileResponse

    @GET("users/{username}/repos")
    suspend fun getUserRepositories(@Path("username") username: Username): List<UserRepositoryResponse>
}