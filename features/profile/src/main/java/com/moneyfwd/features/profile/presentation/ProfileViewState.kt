package com.moneyfwd.features.profile.presentation

import com.moneyfwd.domain.profile.model.ProfileDetails
import com.moneyfwd.domain.profile.model.RepositoryItem

data class ProfileViewState(
    val loading: Boolean = false,
    val profileDetails: ProfileDetails = ProfileDetails(),
    val repositories: List<RepositoryItem> = emptyList()
)