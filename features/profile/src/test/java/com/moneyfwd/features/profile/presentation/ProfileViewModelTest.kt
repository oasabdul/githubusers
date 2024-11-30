package com.moneyfwd.features.profile.presentation

import app.cash.turbine.test
import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.RepositoryItem
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.usecase.GetUserProfileDetailsUseCase
import com.moneyfwd.domain.profile.usecase.GetUserRepositoriesUseCase
import com.moneyfwd.shared.navigation.Navigator
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val testUsername = Username("sampleUser")
    private val testProfileDetails =
        ProfileDetails("https://avatar.url", "sampleUser", "Mark Pain", 10, 5)
    private val testUserRepositories = listOf(
        RepositoryItem("Repo1", "https://repo1.url"),
        RepositoryItem("Repo2", "https://repo2.url")
    )

    private val getUserProfileUseCase: GetUserProfileDetailsUseCase = mockk(relaxed = true)
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase = mockk(relaxed = true)
    private val navigator: Navigator = mockk(relaxed = true)
    private val viewModel: ProfileViewModel =
        ProfileViewModel(getUserProfileUseCase, getUserRepositoriesUseCase, navigator)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadUserProfile should update viewState with loading, profile details, and repositories`() =
        runTest {
            // Given
            coEvery { getUserProfileUseCase(testUsername) } returns GetUserProfileDetailsUseCase.Result.Success(
                testProfileDetails
            )
            coEvery { getUserRepositoriesUseCase(testUsername) } returns GetUserRepositoriesUseCase.Result.Success(
                testUserRepositories
            )

            // When
            viewModel.loadUserProfile(testUsername)

            // Then
            viewModel._viewState.test {
                assertEquals(
                    ProfileViewState(
                        loading = false,
                        profileDetails = testProfileDetails,
                        repositories = testUserRepositories
                    ), awaitItem()
                )
            }

        }

    @Test
    fun `loadUserProfile should update viewState with failure state when use cases fail`() =
        runTest {
            // Given
            val username = Username("sampleUser")
            coEvery { getUserProfileUseCase(username) } returns GetUserProfileDetailsUseCase.Result.Failure
            coEvery { getUserRepositoriesUseCase(username) } returns GetUserRepositoriesUseCase.Result.Failure

            // When
            viewModel.loadUserProfile(username)

            // Then
            viewModel._viewState.test {
                assertEquals(
                    ProfileViewState(
                        loading = false,
                        profileDetails = ProfileDetails(),
                        repositories = emptyList()
                    ), awaitItem()
                )
            }

        }

    @Test
    fun `onBackClick should call navigator goBack`() = runTest {
        // When
        viewModel.onBackClick()
        // Then
        verify { navigator.goBack() }
    }
}
