package com.moneyfwd.data.search.model

data class SearchUserResponse(
    val total_count: Int,
    val items: List<SearchUser>
)