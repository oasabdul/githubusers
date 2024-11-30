package com.moneyfwd.features.search.presentation

import app.cash.turbine.test
import com.moneyfwd.domain.search.model.SearchQuery
import com.moneyfwd.domain.search.model.SearchUserItem
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase.Result.Failure
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase.Result.Success
import com.moneyfwd.shared.navigation.NavDestination.ProfileScreen
import com.moneyfwd.shared.navigation.Navigator
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testQuery = SearchQuery("sampleQuery")
    private val testSearchResults = listOf(
        SearchUserItem("User1"),
        SearchUserItem("User2")
    )

    private val searchGithubUsersUseCase: SearchGithubUsersUseCase = mockk(relaxed = true)
    private val navigator: Navigator = mockk(relaxed = true)
    private val viewModel: SearchViewModel = SearchViewModel(searchGithubUsersUseCase, navigator)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `performSearch should update viewState with loading, and search results`() = runTest {
        // Given
        coEvery { searchGithubUsersUseCase(testQuery) } returns Success(testSearchResults)

        // When
        viewModel.onSearchQueryChanged(testQuery.query)
        advanceTimeBy(1001L) //mock search delay time of 1 second

        // Then
        viewModel._viewState.test {
            assertEquals(
                SearchViewState(
                    loading = false,
                    searchQuery = testQuery.query,
                    searchResults = testSearchResults
                ), awaitItem()
            )
        }
    }

    @Test
    fun `performSearch should update viewState with failure state when use case fails`() = runTest {
        // Given
        coEvery { searchGithubUsersUseCase(testQuery) } returns Failure

        // When
        viewModel.onSearchQueryChanged(testQuery.query)

        // Then
        viewModel._viewState.test {
            assertEquals(
                SearchViewState(
                    loading = false,
                    searchQuery = testQuery.query,
                    searchResults = emptyList()
                ), awaitItem()
            )
        }
    }

    @Test
    fun `onUserItemClicked should navigate to profile screen`() = runTest {
        // Given
        val userItem = SearchUserItem("User1")

        // When
        viewModel.onUserItemClicked(userItem)

        // Then
        coVerify { navigator.navigateTo(ProfileScreen.createRoute(userItem.name)) }
    }
}
