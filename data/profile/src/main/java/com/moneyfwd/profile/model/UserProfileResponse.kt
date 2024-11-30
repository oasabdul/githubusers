package com.moneyfwd.profile.model

import com.moneyfwd.domain.profile.model.ProfileDetails

data class UserProfileResponse(
    val login: String,
    val avatarUrl: String,
    val name: String?,
    val followers: Int,
    val following: Int
) {
    fun mapToDomainModel() = ProfileDetails(
        profilePictureUrl = avatarUrl,
        username = login,
        fullName = name ?: "",
        followerCount = followers,
        followingCount = following
    )
}