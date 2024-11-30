package com.moneyfwd.features.search

import com.moneyfwd.features.search.presentation.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object SearchFeatureKoin {
    val module = module {
        viewModel {
            SearchViewModel(
                searchGithubUsersUseCase = get(),
                navigator = get()
            )
        }
    }
}