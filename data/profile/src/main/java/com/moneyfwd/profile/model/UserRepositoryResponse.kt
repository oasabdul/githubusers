package com.moneyfwd.profile.model

import com.moneyfwd.domain.profile.model.RepositoryItem

data class UserRepositoryResponse(
    val name: String,
    val language: String?,
    val stargazersCount: Int,
    val description: String?,
    val htmlUrl: String,
    val fork: Boolean
) {
    fun mapToDomainModel() = RepositoryItem(
        name = name,
        language = language ?: "",
        stars = stargazersCount,
        description = description ?: "",
        htmlUrl = htmlUrl
    )
}