package com.moneyfwd.domain.profile.usecase

import com.moneyfwd.domain.profile.model.RepositoryItem
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.repository.GetUserRepositoriesResponse
import com.moneyfwd.domain.profile.repository.ProfileRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetUserRepositoriesUseCaseTest {

    private val repository: ProfileRepository = mockk()
    private val useCase = GetUserRepositoriesUseCase(repository)

    private val testUsername = Username("sampleUser")
    private val testRepositories = listOf(
        RepositoryItem("Repo1", "https://repo1.url"),
        RepositoryItem("Repo2", "https://repo2.url")
    )

    @Test
    fun `invoke should return Success when repository returns success`() = runTest {
        // Given
        coEvery { repository.getUserRepositories(testUsername) } returns GetUserRepositoriesResponse.Success(
            testRepositories
        )

        // When
        val result = useCase(testUsername)

        // Then
        assertEquals(GetUserRepositoriesUseCase.Result.Success(testRepositories), result)
    }

    @Test
    fun `invoke should return Failure when repository returns error`() = runTest {
        // Given
        coEvery { repository.getUserRepositories(testUsername) } returns GetUserRepositoriesResponse.UnknownError

        // When
        val result = useCase(testUsername)

        // Then
        assertEquals(GetUserRepositoriesUseCase.Result.Failure, result)
    }
}
