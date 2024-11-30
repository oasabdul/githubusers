package com.moneyfwd.domain

import com.moneyfwd.domain.profile.usecase.GetUserProfileDetailsUseCase
import com.moneyfwd.domain.profile.usecase.GetUserRepositoriesUseCase
import com.moneyfwd.domain.search.usecase.SearchGithubUsersUseCase
import org.koin.dsl.module

object DomainKoin {
    val module = module {
        //useCase modules
        factory { SearchGithubUsersUseCase(repository = get()) }
        factory { GetUserProfileDetailsUseCase(repository = get()) }
        factory { GetUserRepositoriesUseCase(repository = get()) }
    }
}