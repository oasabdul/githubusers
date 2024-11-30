package com.moneyfwd.features.profile

import com.moneyfwd.features.profile.presentation.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ProfileFeatureKoin {
    val module = module {
        viewModel {
            ProfileViewModel(
                getUserProfileUseCase = get(),
                getUserRepositoriesUseCase = get(),
                navigator = get()
            )
        }
    }
}