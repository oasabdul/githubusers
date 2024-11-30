package com.moneyfwd.profile.repository

import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserProfileResponse
import com.moneyfwd.domain.profile.repository.GetUserRepositoriesResponse
import com.moneyfwd.profile.model.UserProfileResponse
import com.moneyfwd.profile.model.UserRepositoryResponse
import com.moneyfwd.profile.network.ProfileApiService
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileRepositoryImplTest {

    private val apiService: ProfileApiService = mockk()
    private val repository = ProfileRepositoryImpl(apiService)

    private val testUsername = Username("sampleUser")
    private val testForkedRepository = UserRepositoryResponse(
        name = "Game repo",
        language = "C++",
        fork = true,
        stargazersCount = 45,
        description = "A repository for car game built with C++",
        htmlUrl = "testUrl"
    )
    private val testRepository = UserRepositoryResponse(
        name = "Game repo",
        language = "C++",
        fork = false,
        stargazersCount = 45,
        description = "A repository for car game built with C++",
        htmlUrl = "testUrl"
    )
    private val testProfileDetailsResponse = UserProfileResponse(
        login = "sampleUser",
        avatarUrl = "sampleUrl",
        name = "Sample Name",
        followers = 32,
        following = 232
    )
    private val testRepositories = listOf(testRepository, testForkedRepository, testRepository)

    @Test
    fun `getUserRepositories should return Success with filtered forked repositories when api returns successfully`() =
        runTest {
            // Given
            coEvery { apiService.getUserRepositories(testUsername) } returns testRepositories

            // When
            val result = repository.getUserRepositories(testUsername)

            // Then - according to requirements, forked repositories should be filtered out
            val expectedRepositories = testRepositories.filterNot { it.fork }.map { it }
            assertEquals(GetUserRepositoriesResponse.Success(expectedRepositories.map { it.mapToDomainModel() }), result)
        }

    @Test
    fun `getUserRepositories should return NetworkError when api throws HttpException`() = runTest {
        // Given
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { apiService.getUserRepositories(testUsername) } throws httpException

        // When
        val result = repository.getUserRepositories(testUsername)

        // Then
        assertEquals(GetUserRepositoriesResponse.NetworkError, result)
    }

    @Test
    fun `getUserRepositories should return UnknownError when api throws generic exception`() =
        runTest {
            // Given
            coEvery { apiService.getUserRepositories(testUsername) } throws Exception()

            // When
            val result = repository.getUserRepositories(testUsername)

            // Then
            assertEquals(GetUserRepositoriesResponse.UnknownError, result)
        }

    @Test
    fun `getUserProfile should return Success when api returns successfully`() = runTest {
        // Given
        coEvery { apiService.getUserProfile(testUsername) } returns testProfileDetailsResponse

        // When
        val result = repository.getUserProfile(testUsername)

        // Then
        assertEquals(GetUserProfileResponse.Success(testProfileDetailsResponse.mapToDomainModel()), result)
    }

    @Test
    fun `getUserProfile should return NetworkError when api throws HttpException`() = runTest {
        // Given
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { apiService.getUserProfile(testUsername) } throws httpException

        // When
        val result = repository.getUserProfile(testUsername)

        // Then
        assertEquals(GetUserProfileResponse.NetworkError, result)
    }

    @Test
    fun `getUserProfile should return UnknownError when api throws generic exception`() = runTest {
        // Given
        coEvery { apiService.getUserProfile(testUsername) } throws Exception()

        // When
        val result = repository.getUserProfile(testUsername)

        // Then
        assertEquals(GetUserProfileResponse.UnknownError, result)
    }
}
