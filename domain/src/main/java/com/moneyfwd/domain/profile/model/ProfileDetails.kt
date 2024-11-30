package com.moneyfwd.domain.profile.model

data class ProfileDetails(
    val profilePictureUrl: String = "",
    val username: String = "",
    val fullName: String = "",
    val followerCount: Int = 0,
    val followingCount: Int = 0
)