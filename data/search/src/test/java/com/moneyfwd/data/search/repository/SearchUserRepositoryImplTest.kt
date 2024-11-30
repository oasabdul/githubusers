package com.moneyfwd.data.search.repository

import com.moneyfwd.data.search.model.SearchUser
import com.moneyfwd.data.search.model.SearchUserResponse
import com.moneyfwd.data.search.network.SearchApiService
import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.repository.SearchUserRepositoryResponse
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException

@OptIn(ExperimentalCoroutinesApi::class)
class SearchUserRepositoryImplTest {

    private lateinit var apiService: SearchApiService
    private lateinit var repository: SearchUserRepositoryImpl

    private val testQuery = SearchQuery("sampleQuery")
    private val testSearchResults = listOf(
        SearchUser(login = "User1", avatar_url = "testUrl"),
        SearchUser(login = "User2", avatar_url = "testUrl")
    )

    @Before
    fun setUp() {
        apiService = mockk()
        repository = SearchUserRepositoryImpl(apiService)
    }

    @Test
    fun `searchUsers should return Success when api returns successfully`() = runTest {
        // Given
        coEvery { apiService.searchUsers(testQuery.query) } returns SearchUserResponse(
            total_count = 2,
            items = testSearchResults
        )

        // When
        val result = repository.searchUsers(testQuery)

        // Then
        assertEquals(SearchUserRepositoryResponse.Success(testSearchResults.map { it.mapToDomainModel() }), result)
    }

    @Test
    fun `searchUsers should return NetworkError when api throws HttpException`() = runTest {
        // Given
        val httpException = mockk<HttpException>(relaxed = true)
        coEvery { apiService.searchUsers(testQuery.query) } throws httpException

        // When
        val result = repository.searchUsers(testQuery)

        // Then
        assertEquals(SearchUserRepositoryResponse.NetworkError, result)
    }

    @Test
    fun `searchUsers should return UnknownError when api throws generic exception`() = runTest {
        // Given
        coEvery { apiService.searchUsers(testQuery.query) } throws Exception()

        // When
        val result = repository.searchUsers(testQuery)

        // Then
        assertEquals(SearchUserRepositoryResponse.UnknownError, result)
    }
}
