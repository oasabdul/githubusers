package com.moneyfwd.data.search

import com.moneyfwd.data.search.network.SearchApiService
import com.moneyfwd.data.search.repository.SearchUserRepositoryImpl
import com.moneyfwd.domain.search.repository.SearchUsersRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

object SearchDataKoin {
    val module = module {
        single {
            get<Retrofit>().create<SearchApiService>()
        }
        factory<SearchUsersRepository> {
            SearchUserRepositoryImpl(apiService = get())
        }
    }
}