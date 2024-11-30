package com.moneyfwd.domain.search.usecase

import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.model.SearchUserItem
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse
import com.moneyfwd.domain.search.repository.SearchUsersRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchGithubUsersUseCaseTest {

    private val repository: SearchUsersRepository = mockk()
    private val useCase: SearchGithubUsersUseCase = SearchGithubUsersUseCase(repository)

    private val testQuery = SearchQuery("sampleQuery")
    private val testSearchResults = listOf(
        SearchUserItem("User1"),
        SearchUserItem("User2")
    )

    @Test
    fun `invoke should return Success when repository returns success`() = runTest {
        // Given
        coEvery { repository.searchUsers(testQuery) } returns SearchUserRepositoryResponse.Success(
            testSearchResults
        )

        // When
        val result = useCase(testQuery)

        // Then
        assertEquals(SearchGithubUsersUseCase.Result.Success(testSearchResults), result)
    }

    @Test
    fun `invoke should return Failure when repository returns error`() = runTest {
        // Given
        coEvery { repository.searchUsers(testQuery) } returns SearchUserRepositoryResponse.UnknownError

        // When
        val result = useCase(testQuery)

        // Then
        assertEquals(SearchGithubUsersUseCase.Result.Failure, result)
    }
}