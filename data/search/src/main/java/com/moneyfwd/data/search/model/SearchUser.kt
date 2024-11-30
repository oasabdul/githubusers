package com.moneyfwd.data.search.model

import com.moneyfwd.domain.search.model.SearchUserItem

data class SearchUser(
    val login: String,
    val avatar_url: String
) {
    fun mapToDomainModel(): SearchUserItem = SearchUserItem(name = login, thumbImgUrl = avatar_url)
}