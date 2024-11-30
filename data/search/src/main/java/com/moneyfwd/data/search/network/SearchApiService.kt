package com.moneyfwd.data.search.network

import com.moneyfwd.data.search.model.SearchUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchApiService {
    @GET("search/users")
    suspend fun searchUsers(@Query("q") query: String): SearchUserResponse
}