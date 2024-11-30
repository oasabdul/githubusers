package com.moneyfwd.features.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.Username
import com.moneyfwd.domain.profile.usecase.GetUserProfileDetailsUseCase
import com.moneyfwd.domain.profile.usecase.GetUserRepositoriesUseCase
import com.moneyfwd.shared.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserProfileUseCase: GetUserProfileDetailsUseCase,
    private val getUserRepositoriesUseCase: GetUserRepositoriesUseCase,
    private val navigator: Navigator
) : ViewModel() {

    val _viewState = MutableStateFlow(ProfileViewState())
    private val viewState: StateFlow<ProfileViewState> = _viewState

    private fun updateViewState(newState: ProfileViewState) {
        _viewState.value = newState
    }

    fun loadUserProfile(username: Username) = viewModelScope.launch {
        updateViewState(viewState.value.copy(loading = true))

        val profile = getUserProfileUseCase(username)
        val userRepositories = getUserRepositoriesUseCase(username)

        when (profile) {
            is GetUserProfileDetailsUseCase.Result.Success -> updateViewState(
                viewState.value.copy(loading = false, profileDetails = profile.profileDetails)
            )

            else -> updateViewState(
                viewState.value.copy(
                    loading = false,
                    profileDetails = ProfileDetails()
                )
            )
        }

        when (userRepositories) {
            is GetUserRepositoriesUseCase.Result.Success -> updateViewState(
                viewState.value.copy(loading = false, repositories = userRepositories.repositories)
            )

            else -> updateViewState(
                viewState.value.copy(
                    loading = false,
                    repositories = emptyList()
                )
            )
        }
    }

    fun onBackClick() {
        navigator.goBack()
    }
}