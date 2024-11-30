package com.moneyfwd.domain.profile.model

data class RepositoryItem(
    val name: String  = "",
    val language: String = "",
    val stars: Int = 0,
    val description: String = "",
    val htmlUrl: String = ""
)