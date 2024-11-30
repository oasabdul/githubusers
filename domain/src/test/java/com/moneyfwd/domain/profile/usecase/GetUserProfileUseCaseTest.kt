package com.moneyfwd.domain.profile.usecase

import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserProfileResponse
import com.moneyfwd.domain.profile.repository.ProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserProfileDetailsUseCaseTest {

    private val repository: ProfileRepository = mockk()
    private val useCase = GetUserProfileDetailsUseCase(repository)

    private val testUsername = Username("sampleUser")
    private val testProfileDetails =
        ProfileDetails(
            profilePictureUrl = "https://avatar.url",
            username = "sampleUser",
            fullName = "Mark Pain",
            followerCount = 10,
            followingCount = 5
        )

    @Test
    fun `invoke should return Success when repository returns success`() = runTest {
        // Given
        coEvery { repository.getUserProfile(testUsername) } returns GetUserProfileResponse.Success(
            testProfileDetails
        )

        // When
        val result = useCase(testUsername)

        // Then
        assertEquals(GetUserProfileDetailsUseCase.Result.Success(testProfileDetails), result)
    }

    @Test
    fun `invoke should return Failure when repository returns error`() = runTest {
        // Given
        coEvery { repository.getUserProfile(testUsername) } returns GetUserProfileResponse.UnknownError

        // When
        val result = useCase(testUsername)

        // Then
        assertEquals(GetUserProfileDetailsUseCase.Result.Failure, result)
    }
}
